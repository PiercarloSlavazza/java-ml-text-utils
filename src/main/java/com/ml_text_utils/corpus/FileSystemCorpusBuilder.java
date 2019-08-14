package com.ml_text_utils.corpus;

import com.ml_text_utils.ClassLabel;
import com.ml_text_utils.TextDocumentsStream;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.ml_text_utils.utils.FileUtils.ensureFileExists;
import static com.ml_text_utils.utils.FileUtils.ensureFileIsFolder;

@SuppressWarnings("unused") public class FileSystemCorpusBuilder {

    private final static Logger log = LoggerFactory.getLogger(FileSystemCorpusBuilder.class);

    private static final Pattern RETAIN_NUMBER_AND_ENGLISH_LETTERS = Pattern.compile("[^a-zA-Z0-9]");
    private static final String TRAINING_FOLDER_NAME = "training";
    private static final String TEST_FOLDER_NAME = "test";

    private final Random random = new Random();

    private final Predicate<CorpusClassStatistics> corpusClassStatisticsFilter;

    public FileSystemCorpusBuilder() {
	this((corpusClassStatistics) -> true);
    }

    @SuppressWarnings("WeakerAccess") public FileSystemCorpusBuilder(Predicate<CorpusClassStatistics> corpusClassStatisticsFilter) {
	this.corpusClassStatisticsFilter = corpusClassStatisticsFilter;
    }

    private boolean hasToBeTrainingDocument(double trainingSetRate) {
	return random.nextDouble() <= trainingSetRate;
    }

    private synchronized void incrementDocumentCount(Map<ClassLabel, Integer> documentCountByClass, ClassLabel classLabel) {
	Integer documentCountOfClass = Optional.ofNullable(documentCountByClass.get(classLabel)).orElse(0);
	documentCountByClass.put(classLabel, ++documentCountOfClass);
    }

    private String normalizeAsFileSystemName(String name) {
	return RETAIN_NUMBER_AND_ENGLISH_LETTERS.matcher(name).replaceAll("_").toLowerCase();
    }

    private void writeUnchecked(File file, CharSequence data) {
	try {
	    FileUtils.write(file, data, "UTF-8");
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    private String printSortedClassesCSV(Set<ClassLabel> trainingClassLabels) {
	return trainingClassLabels.stream().map(ClassLabel::getName).sorted().collect(Collectors.joining(","));
    }

    private List<CorpusClassStatistics> removeClassesFromStatistics(List<ClassLabel> classesToBeExcluded,
								    List<CorpusClassStatistics> corpusClassesStatistics) {
	return corpusClassesStatistics.stream().
			filter(corpusClassStatistics -> !classesToBeExcluded.contains(corpusClassStatistics.getClassLabel())).
			collect(Collectors.toList());
    }

    private void removeClassesFromFileSystem(List<ClassLabel> classesToBeExcluded, File corpusFolder) {
	classesToBeExcluded.stream().map(ClassLabel::getName).forEach(classLabel -> {
	    File trainFolderToBeRemoved = new File(corpusFolder.getPath() + File.separator + TRAINING_FOLDER_NAME + File.separator + classLabel);
	    FileUtils.deleteQuietly(trainFolderToBeRemoved);

	    File testFolderToBeRemoved = new File(corpusFolder.getPath() + File.separator + TEST_FOLDER_NAME + File.separator + classLabel);
	    FileUtils.deleteQuietly(testFolderToBeRemoved);
	});
    }

    private void ensureTrainingClassesMatchesTestClasses(Map<ClassLabel, Integer> trainingDocumentsCountByClass,
							 Map<ClassLabel, Integer> testDocumentsCountByClass,
							 List<ClassLabel> classesToBeExcluded) {

	Set<ClassLabel> trainingClassLabels = trainingDocumentsCountByClass.keySet();
	trainingClassLabels.removeAll(classesToBeExcluded);

	Set<ClassLabel> testClassLabels = testDocumentsCountByClass.keySet();
	testClassLabels.removeAll(classesToBeExcluded);

	if (!trainingClassLabels.equals(testClassLabels))
	    throw new RuntimeException(String.format("training documents and test documents classes do not match|training classes|%s|test classes|%s",
						     printSortedClassesCSV(trainingClassLabels),
						     printSortedClassesCSV(testClassLabels)));

    }

    @SuppressWarnings("unused") public CorpusStatistics buildCorpusBySplittingTrainingAndTestSet(TextDocumentsStream textDocumentsStream,
												 File corpusFolder,
												 double trainingSetRate) {

	ensureFileExists(corpusFolder);
	ensureFileIsFolder(corpusFolder);

	File trainingFolder = new File(corpusFolder + File.separator + TRAINING_FOLDER_NAME);
	File testFolder = new File(corpusFolder + File.separator + TEST_FOLDER_NAME);

	Map<ClassLabel, Integer> trainingDocumentsCountByClass = new HashMap<>();
	Map<ClassLabel, Integer> testDocumentsCountByClass = new HashMap<>();

	textDocumentsStream.
			streamTextDocuments().
			forEach(textDocument -> {
			    boolean hasToBeTrainingDocument = hasToBeTrainingDocument(trainingSetRate);

			    String classLabelFolderName = normalizeAsFileSystemName(textDocument.getClassLabel().getName());
			    String documentFileName = normalizeAsFileSystemName(textDocument.getId()) + ".txt";
			    String documentClassSubPath = classLabelFolderName + File.separator + documentFileName;
			    File documentPath = new File((hasToBeTrainingDocument ? trainingFolder : testFolder) + File.separator + documentClassSubPath);

			    incrementDocumentCount(hasToBeTrainingDocument ? trainingDocumentsCountByClass : testDocumentsCountByClass,
						   textDocument.getClassLabel());

			    writeUnchecked(documentPath, textDocument.getText());
			});

	List<CorpusClassStatistics> corpusClassesStatistics = trainingDocumentsCountByClass.keySet().stream().
			map(classLabel -> {
			    Integer trainingDocuments = trainingDocumentsCountByClass.get(classLabel);
			    Integer testDocuments = testDocumentsCountByClass.get(classLabel);
			    return new CorpusClassStatistics(trainingDocuments, testDocuments, classLabel);
			}).
			sorted(Comparator.comparing(corpusClassStatistics -> corpusClassStatistics.getClassLabel().getName())).
			collect(Collectors.toList());
	List<ClassLabel> classesToBeExcluded = corpusClassesStatistics.
			stream().
			filter(corpusClassStatistics -> !corpusClassStatisticsFilter.test(corpusClassStatistics)).
			map(CorpusClassStatistics::getClassLabel).
			collect(Collectors.toList());

	log.info("excluded classes|" + classesToBeExcluded.stream().map(ClassLabel::getName).collect(Collectors.joining(",")));

	removeClassesFromFileSystem(classesToBeExcluded, corpusFolder);
	corpusClassesStatistics = removeClassesFromStatistics(classesToBeExcluded, corpusClassesStatistics);

	ensureTrainingClassesMatchesTestClasses(trainingDocumentsCountByClass, testDocumentsCountByClass, classesToBeExcluded);

	return new CorpusStatistics(corpusClassesStatistics.stream().mapToInt(CorpusClassStatistics::getTrainingDocumentsCount).sum(),
				    corpusClassesStatistics.stream().mapToInt(CorpusClassStatistics::getTestDocumentsCount).sum(),
				    corpusClassesStatistics);
    }

}

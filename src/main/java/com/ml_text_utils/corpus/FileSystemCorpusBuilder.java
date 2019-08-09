package com.ml_text_utils.corpus;

import com.ml_text_utils.ClassLabel;
import com.ml_text_utils.TextDocumentsStream;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SuppressWarnings("unused") public class FileSystemCorpusBuilder {

    private static final Pattern RETAIN_NUMBER_AND_ENGLISH_LETTERS = Pattern.compile("[^a-zA-Z0-9]");
    private static final String TRAINING_FOLDER_NAME = "training";
    private static final String TEST_FOLDER_NAME = "test";

    private final Random random = new Random();

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

    @SuppressWarnings("unused") public CorpusStatistics buildCorpusBySplittingTrainingAndTestSet(TextDocumentsStream textDocumentsStream,
												 File corpusFolder,
												 double trainingSetRate) {

	if (!corpusFolder.exists()) throw new RuntimeException("corpus folder does not exists|folder|" + corpusFolder);
	if (!corpusFolder.isDirectory()) throw new RuntimeException("corpus folder is actually not a folder|folder|" + corpusFolder);

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

	Set<ClassLabel> trainingClassLabels = trainingDocumentsCountByClass.keySet();
	Set<ClassLabel> testClassLabels = testDocumentsCountByClass.keySet();
	if (!trainingClassLabels.equals(testClassLabels))
	    throw new RuntimeException(String.format("training documents and test documents classes do not match|training classes|%s|test classes|%s",
						     printSortedClassesCSV(trainingClassLabels),
						     printSortedClassesCSV(testClassLabels)));

	List<CorpusClassStatistics> corpusClassesStatistics = trainingClassLabels.stream().
			map(classLabel -> {
			    Integer trainingDocuments = trainingDocumentsCountByClass.get(classLabel);
			    Integer testDocuments = testDocumentsCountByClass.get(classLabel);
			    return new CorpusClassStatistics(trainingDocuments, testDocuments, classLabel);
			}).
			sorted(Comparator.comparing(corpusClassStatistics -> corpusClassStatistics.getClassLabel().getName())).
			collect(Collectors.toList());

	return new CorpusStatistics(corpusClassesStatistics.stream().mapToInt(CorpusClassStatistics::getTrainingDocumentsCount).sum(),
				    corpusClassesStatistics.stream().mapToInt(CorpusClassStatistics::getTestDocumentsCount).sum(),
				    corpusClassesStatistics);
    }



}

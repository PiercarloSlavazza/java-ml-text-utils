package com.ml_text_utils.corpus;

import com.ml_text_utils.nlp.NLPPreprocessor;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import static com.ml_text_utils.utils.FileUtils.ensureFileExists;
import static com.ml_text_utils.utils.FileUtils.ensureFileIsFolder;

@SuppressWarnings("ConstantConditions") public class NLPCorpusPreprocesser {

    private final static Logger log = LoggerFactory.getLogger(NLPCorpusPreprocesser.class);

    private static final FilenameFilter TEXT_FILENAME_FILTER = (dir, name) -> name.toLowerCase().endsWith(".txt");

    private final NLPPreprocessor nlpPreprocessor;

    public NLPCorpusPreprocesser(NLPPreprocessor nlpPreprocessor) {
	this.nlpPreprocessor = nlpPreprocessor;
    }

    private void preprocessFile(File file, File preprocessedCorpusSubFolder) {
        log.info("preprocessing file|" + file.getName());
	File preprocessedFile = new File(preprocessedCorpusSubFolder.getPath() + File.separator + file.getName());
	try {
	    String textToBePreprocessed = FileUtils.readFileToString(file, "UTF-8");
	    String textPreprocessed = nlpPreprocessor.preprocess(textToBePreprocessed);
	    FileUtils.writeStringToFile(preprocessedFile, textPreprocessed, "UTF-8");
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    private Stream<File> streamTextFiles(File folder) {
	return Arrays.stream(folder.listFiles(TEXT_FILENAME_FILTER));
    }

    private Stream<File> streamSubFolders(File folder) {
	return Arrays.stream(folder.listFiles(File::isDirectory));
    }

    private void preprocessCorpusSubFolder(File corpusSubfolder, File preprocessedCorpusParentFolder) {

        log.info("preprocessing subfolder|" + corpusSubfolder);

        File preprocessedCorpusSubFolder = new File(preprocessedCorpusParentFolder.getPath() + File.separator + corpusSubfolder.getName());
        if (preprocessedCorpusSubFolder.exists()) throw new RuntimeException("cannot proceed: preprocessed corpus subfolder already exists|" + preprocessedCorpusSubFolder);

	try {
	    FileUtils.forceMkdir(preprocessedCorpusSubFolder);

	    streamTextFiles(corpusSubfolder).parallel().forEach(file -> preprocessFile(file, preprocessedCorpusSubFolder));
	    streamSubFolders(corpusSubfolder).forEach(folder -> preprocessCorpusSubFolder(folder, preprocessedCorpusSubFolder));
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public void preprocessCorpus(File corpusRootFolder, File preprocessedCorpusRootFolder) {

        log.info("preprocessing corpus|" + corpusRootFolder + "|to|" + preprocessedCorpusRootFolder);

	ensureFileExists(corpusRootFolder);
	ensureFileIsFolder(corpusRootFolder);

	ensureFileExists(preprocessedCorpusRootFolder);
	ensureFileIsFolder(preprocessedCorpusRootFolder);

	streamSubFolders(corpusRootFolder).forEach(folder -> preprocessCorpusSubFolder(folder, preprocessedCorpusRootFolder));
    }

}

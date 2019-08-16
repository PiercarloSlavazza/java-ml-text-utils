package com.ml_text_utils.libsvm;

import com.ml_text_utils.features.DocumentAsLabeledPoint;
import com.ml_text_utils.features.FeaturesExtractor;
import com.ml_text_utils.utils.FileUtils;
import com.ml_text_utils.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.atomic.AtomicInteger;

import static com.ml_text_utils.utils.FileUtils.ensureFileExists;
import static com.ml_text_utils.utils.FileUtils.ensureFileIsFolder;

public class LibSVMCorpusFormatter {

    private final static Logger log = LoggerFactory.getLogger(LibSVMCorpusFormatter.class);

    private final FeaturesExtractor featuresExtractor;
    private final LibSVMLabeledPointFormatter libSVMLabeledPointFormatter;

    public LibSVMCorpusFormatter(FeaturesExtractor featuresExtractor, LibSVMLabeledPointFormatter libSVMLabeledPointFormatter) {
	this.featuresExtractor = featuresExtractor;
	this.libSVMLabeledPointFormatter = libSVMLabeledPointFormatter;
    }

    private String getDocumentId(File file) {
	return file.getName();
    }

    private String getClassLabel(File file) {
	return file.getParentFile().getName();
    }

    private DocumentAsLabeledPoint toDocumentAsLabeledPoint(File file) {
	String documentId = getDocumentId(file);
	String classLabel = getClassLabel(file);
	return featuresExtractor.extractFeaturesFromDocumentInCorpus(documentId, classLabel);
    }

    private void exportToLibSVMFormat(File classesParentFolder, File libSVMExportFile, AtomicInteger exportedFilesCount) {

	ensureFileExists(classesParentFolder);
        ensureFileIsFolder(classesParentFolder);

	try (Writer writer = new FileWriter(libSVMExportFile)) {
	    FileUtils.streamTextFilesInSubFolders(classesParentFolder).
			    parallel().
			    peek(file -> log.info(exportedFilesCount.incrementAndGet() + "|class|" + getClassLabel(file) + "|exporting to libsvm file|" + file)).
			    map(this::toDocumentAsLabeledPoint).
			    map(DocumentAsLabeledPoint::getLabeledPoint).
			    map(libSVMLabeledPointFormatter::formatAsLibSVMRow).
			    forEach(libSVMLine -> IOUtils.writeln(libSVMLine, writer));
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public void exportToLibSVMFormat(File trainingSetFolder,
				     File testSetFolder,
				     File libSVMExportFolder,
				     String libSVMExportFilePrefix) {

        AtomicInteger exportedFilesCount = new AtomicInteger(0);

        log.info("exporting training set|start|folder|" + trainingSetFolder);
        File libSVMExportFile = new File(libSVMExportFolder.getPath() + File.separator + libSVMExportFilePrefix + "_training.libsvm");
        exportToLibSVMFormat(trainingSetFolder, libSVMExportFile, exportedFilesCount);
	log.info("exporting training set|end");


	log.info("exporting test set|start|folder|" + testSetFolder);
	libSVMExportFile = new File(libSVMExportFolder.getPath() + File.separator + libSVMExportFilePrefix + "_test.libsvm");
	exportToLibSVMFormat(testSetFolder, libSVMExportFile, exportedFilesCount);
	log.info("exporting test set|end");
    }

}

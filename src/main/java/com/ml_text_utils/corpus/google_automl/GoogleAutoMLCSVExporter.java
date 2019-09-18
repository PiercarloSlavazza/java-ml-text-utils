/*
 * Copyright  2019 Piercarlo Slavazza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.ml_text_utils.corpus.google_automl;

import com.ml_text_utils.corpus.FileSystemCorpusBuilder;
import com.ml_text_utils.utils.FileUtils;
import com.ml_text_utils.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class GoogleAutoMLCSVRow {

    public enum DataSet {
	TRAIN, TEST, VALIDATION
    }

    private final URI documentUrl;
    private final String classLabel;
    private final DataSet dataSet;

    GoogleAutoMLCSVRow(URI documentUrl, String classLabel, DataSet dataSet) {
	this.documentUrl = documentUrl;
	this.classLabel = classLabel;
	this.dataSet = dataSet;
    }

    DataSet getDataSet() {
	return dataSet;
    }

    URI getDocumentUrl() {
	return documentUrl;
    }

    String getClassLabel() {
	return classLabel;
    }

    @Override public String toString() {
	return "GoogleAutoMLCSVRow{" +
			"documentUrl=" + documentUrl +
			", classLabel='" + classLabel + '\'' +
			", dataSet=" + dataSet +
			'}';
    }

    @Override public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof GoogleAutoMLCSVRow))
	    return false;
	GoogleAutoMLCSVRow that = (GoogleAutoMLCSVRow) o;
	return Objects.equals(documentUrl, that.documentUrl) &&
			Objects.equals(classLabel, that.classLabel) &&
			dataSet == that.dataSet;
    }

    @Override public int hashCode() {
	return Objects.hash(documentUrl, classLabel, dataSet);
    }
}

public class GoogleAutoMLCSVExporter {

    private final static Logger log = LoggerFactory.getLogger(GoogleAutoMLCSVExporter.class);

    private final String googleCloudStorageFolderURI;
    private final Map<String, Random> validationSetSampler = new HashMap<>();

    public GoogleAutoMLCSVExporter(String googleCloudStorageFolderURI) {
	this.googleCloudStorageFolderURI = googleCloudStorageFolderURI;
    }

    private GoogleAutoMLCSVRow toGoogleAutoMLCSVRow(File file) {
	try {
	    File classFolder = file.getParentFile();
	    String classLabel = classFolder.getName();

	    String trainingOrTestFolderName = classFolder.getParentFile().getName();

	    GoogleAutoMLCSVRow.DataSet dataSet = chooseDataSet(classLabel, trainingOrTestFolderName);

	    URI googleCloudStorageURI = new URI(googleCloudStorageFolderURI + "/" + trainingOrTestFolderName + "/" + classLabel + "/" + file.getName());
	    return new GoogleAutoMLCSVRow(googleCloudStorageURI, classLabel, dataSet);
	} catch (URISyntaxException e) {
	    throw new RuntimeException(e);
	}
    }

    private boolean docShouldBInValidationDataSet(String classLabel) {
	Random randomForClass = Optional.ofNullable(validationSetSampler.get(classLabel)).orElseGet(() -> {
	    Random random = new Random();
	    validationSetSampler.put(classLabel, random);
	    return random;
	});
	return randomForClass.nextDouble() >= 0.9;
    }

    private GoogleAutoMLCSVRow.DataSet chooseDataSet(String classLabel, String trainingOrTestFolderName) {
	if (trainingOrTestFolderName.equals(FileSystemCorpusBuilder.TEST_FOLDER_NAME)) return GoogleAutoMLCSVRow.DataSet.TEST;
	if (docShouldBInValidationDataSet(classLabel)) return GoogleAutoMLCSVRow.DataSet.VALIDATION;
	return GoogleAutoMLCSVRow.DataSet.TRAIN;
    }

    private String getDataSetLabel(GoogleAutoMLCSVRow row) {
	switch (row.getDataSet()) {
	    case TEST:
		return "TEST";
	    case VALIDATION:
		return "VALIDATION";
	    case TRAIN:
		return "TRAIN";
	    default:
		throw new RuntimeException("unknown data set|" + row.getDataSet());
	}
    }

    private void writeGoogleAutoMLCSVRow(GoogleAutoMLCSVRow row, Writer writer) {
	IOUtils.writeln(getDataSetLabel(row) + "," + row.getDocumentUrl() + "," + row.getClassLabel(), writer);
    }

    public void exportCorpusToCSV(File corpusRootFolder, File googleAutoMLCSV) {

	FileUtils.ensureFileExists(corpusRootFolder);
	FileUtils.ensureFileIsFolder(corpusRootFolder);

	AtomicInteger filesExported = new AtomicInteger(0);
	try (Writer write = new FileWriter(googleAutoMLCSV)) {
	    FileUtils.streamTextFilesInSubFolders(corpusRootFolder).
			    peek(file -> log.info(filesExported.incrementAndGet() + "|exporting|" + file)).
			    map(this::toGoogleAutoMLCSVRow).
			    forEach(googleAutoMLCSVRow -> writeGoogleAutoMLCSVRow(googleAutoMLCSVRow, write));
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

}

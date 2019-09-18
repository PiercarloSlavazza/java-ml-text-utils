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

package com.ml_text_utils.corpus.onehot_encoding_tsv;

import com.ml_text_utils.corpus.FileSystemCorpusBuilder;
import com.ml_text_utils.corpus.google_automl.GoogleAutoMLCSVExporter;
import com.ml_text_utils.utils.FileUtils;
import com.ml_text_utils.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class OneHotEncodingTSVExporter {

    private final static Logger log = LoggerFactory.getLogger(GoogleAutoMLCSVExporter.class);

    private final static Predicate<File> IS_NOT_COPYRIGHT_FILE = (file) -> !file.getName().equals("copyright_notices.txt");

    private final Map<String, Random> validationSetSampler = new HashMap<>();
    private final OneHotEncoderFactory oneHotEncoderFactory;

    public OneHotEncodingTSVExporter(OneHotEncoderFactory oneHotEncoderFactory) {
	this.oneHotEncoderFactory = oneHotEncoderFactory;
    }

    private OneHotEncodingTSVRow toOneHotEncodingTSVRow(File file, OneHotEncoder oneHotEncoder) {
	try {
	    File classFolder = file.getParentFile();
	    String classLabel = classFolder.getName();

	    String trainingOrTestFolderName = classFolder.getParentFile().getName();

	    OneHotEncodingTSVRow.DataSet dataSet = chooseDataSet(classLabel, trainingOrTestFolderName);
	    OneHotEncoding oneHotEncoding  = oneHotEncoder.encodeOneHot(classLabel);
	    return new OneHotEncodingTSVRow(oneHotEncoding, org.apache.commons.io.FileUtils.readFileToString(file, "UTF-8"), dataSet);
	} catch (IOException e) {
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

    private OneHotEncodingTSVRow.DataSet chooseDataSet(String classLabel, String trainingOrTestFolderName) {
	if (trainingOrTestFolderName.equals(FileSystemCorpusBuilder.TEST_FOLDER_NAME)) return OneHotEncodingTSVRow.DataSet.TEST;
	if (docShouldBInValidationDataSet(classLabel)) return OneHotEncodingTSVRow.DataSet.VALIDATION;
	return OneHotEncodingTSVRow.DataSet.TRAIN;
    }

    private void writeOnehorEncodingTSVRow(OneHotEncodingTSVRow row, Writer trainWriter, Writer devWriter, Writer testWriter) {
	String textNormalized = StringUtils.replace(row.getText(), "\n", " ");
	textNormalized = StringUtils.replace(textNormalized, "\r", " ");
	textNormalized = StringUtils.replace(textNormalized, "\t", " ");

	String tsvLine =  row.getOneHotEncoding() + "\t" + textNormalized;
	switch (row.getDataSet()) {
	    case TRAIN:
		IOUtils.writeln(tsvLine, trainWriter);
		break;
	    case VALIDATION:
		IOUtils.writeln(tsvLine, devWriter);
		break;
	    case TEST:
		IOUtils.writeln(tsvLine, testWriter);
		break;
	    default:
		throw new RuntimeException("unknown dataset|" + row.getDataSet());
	}

    }

    private Set<String> collectClassesLabels(File corpusRootFolder) {
        return FileUtils.streamTextFilesInSubFolders(corpusRootFolder).
			filter(IS_NOT_COPYRIGHT_FILE).
			map(file -> file.getParentFile().getName()).
			collect(Collectors.toSet());
    }

    public void exportCorpusToTSVs(File corpusRootFolder, File trainTSVFile, File devTSVFile, File testTSVFile) {

	FileUtils.ensureFileExists(corpusRootFolder);
	FileUtils.ensureFileIsFolder(corpusRootFolder);

	Set<String> classesLabels = collectClassesLabels(corpusRootFolder);
	OneHotEncoder oneHotEncoder = oneHotEncoderFactory.buildOneHotEncoder(classesLabels);

	AtomicInteger filesExported = new AtomicInteger(0);
	try (Writer trainWriter = new FileWriter(trainTSVFile); Writer devWriter = new FileWriter(devTSVFile); Writer testWriter = new FileWriter(testTSVFile)) {
	    FileUtils.streamTextFilesInSubFolders(corpusRootFolder).
			    filter(IS_NOT_COPYRIGHT_FILE).
			    peek(file -> log.info(filesExported.incrementAndGet() + "|exporting|" + file)).
			    map(file -> toOneHotEncodingTSVRow(file, oneHotEncoder)).
			    forEach(oneHotEncodingTSVRow -> writeOnehorEncodingTSVRow(oneHotEncodingTSVRow, trainWriter, devWriter, testWriter));
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

}

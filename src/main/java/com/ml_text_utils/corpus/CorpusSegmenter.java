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

package com.ml_text_utils.corpus;

import com.ml_text_utils.nlp.SentenceSplitter;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static com.ml_text_utils.utils.FileUtils.*;

public class CorpusSegmenter {

    private final static Logger log = LoggerFactory.getLogger(CorpusSegmenter.class);

    private final SentenceSplitter sentenceSplitter;

    public CorpusSegmenter(SentenceSplitter sentenceSplitter) {
	this.sentenceSplitter = sentenceSplitter;
    }

    private void segmentFileOneSentencePerLine(File file, File segmentedCorpusSubFolder) {
	log.info("segmenting file|" + file.getName());
	File segmentedFile = new File(segmentedCorpusSubFolder.getPath() + File.separator + file.getName());
	try {
	    String textToBeSegmented = FileUtils.readFileToString(file, "UTF-8");
	    String textSegmented = String.join("\n", sentenceSplitter.split(textToBeSegmented));
	    FileUtils.writeStringToFile(segmentedFile, textSegmented, "UTF-8");
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    private void segmentCorpusSubfolderOneSentencePerLine(File corpusSubfolder, File segmentedCorpusRootFolder) {

	log.info("segmenting subfolder|" + corpusSubfolder);

	File segmentedCorpusSubFolder = new File(segmentedCorpusRootFolder.getPath() + File.separator + corpusSubfolder.getName());
	if (segmentedCorpusSubFolder.exists()) throw new RuntimeException("cannot proceed: segmented corpus subfolder already exists|" + segmentedCorpusSubFolder);

	try {
	    FileUtils.forceMkdir(segmentedCorpusSubFolder);

	    streamTextFiles(corpusSubfolder).parallel().forEach(file -> segmentFileOneSentencePerLine(file, segmentedCorpusSubFolder));
	    streamSubFolders(corpusSubfolder).forEach(folder -> segmentCorpusSubfolderOneSentencePerLine(folder, segmentedCorpusSubFolder));
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public void segmentCorpusOneSentencePerLine(File corpusRootFolder, File segmentedCorpusRootFolder) {

	log.info("segmenting corpus|" + corpusRootFolder + "|to|" + segmentedCorpusRootFolder);

	ensureFileExists(corpusRootFolder);
	ensureFileIsFolder(corpusRootFolder);

	ensureFileExists(segmentedCorpusRootFolder);
	ensureFileIsFolder(segmentedCorpusRootFolder);

	streamSubFolders(corpusRootFolder).forEach(folder -> segmentCorpusSubfolderOneSentencePerLine(folder, segmentedCorpusRootFolder));
    }

}

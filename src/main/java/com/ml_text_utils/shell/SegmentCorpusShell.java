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

package com.ml_text_utils.shell;

import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.Option;
import com.ml_text_utils.corpus.CorpusSegmenter;
import com.ml_text_utils.nlp.NLPProcessorFactoryRegistry;
import com.ml_text_utils.nlp.SentenceSplitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

interface SegmentCorpusShellConfig {

    @Option
    File getCorpusFolderRoot();

    @Option
    File getSegmentedCorpusFolderRoot();

    @Option
    String getIso6391Language();
}

public class SegmentCorpusShell {

    private final static Logger log = LoggerFactory.getLogger(SegmentCorpusShell.class);

    public static void main(String[] args) {

	SegmentCorpusShellConfig config = CliFactory.parseArguments(SegmentCorpusShellConfig.class, args);
	log.info("start|configs" + config.toString());

	SentenceSplitter sentenceSplitter = NLPProcessorFactoryRegistry.getNLPProcessorFactoryByLanguage(config.getIso6391Language()).buildSentenceSplitter();
	CorpusSegmenter corpusSegmenter = new CorpusSegmenter(sentenceSplitter);

	corpusSegmenter.segmentCorpusOneSentencePerLine(config.getCorpusFolderRoot(), config.getSegmentedCorpusFolderRoot());
    }

}

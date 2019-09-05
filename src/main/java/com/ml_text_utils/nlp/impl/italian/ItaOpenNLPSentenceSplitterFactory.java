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

package com.ml_text_utils.nlp.impl.italian;

import com.ml_text_utils.nlp.SentenceSplitter;
import com.ml_text_utils.nlp.SentenceSplitterFactory;
import com.ml_text_utils.nlp.impl.opennlp.OpenNLPSentenceSplitter;
import opennlp.tools.sentdetect.SentenceModel;

import java.io.IOException;
import java.io.InputStream;

import static com.ml_text_utils.utils.Classpath.getResourceAsStream;

public class ItaOpenNLPSentenceSplitterFactory implements SentenceSplitterFactory {

    private final SentenceModel sentenceDetectorModel;

    ItaOpenNLPSentenceSplitterFactory() {
	String openNLPSentenceSplitterModelFileName = "it-sent.bin";

	try (InputStream sentenceSplitterModelStream = getResourceAsStream(openNLPSentenceSplitterModelFileName)) {
	    sentenceDetectorModel = new SentenceModel(sentenceSplitterModelStream);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override public SentenceSplitter buildSentenceSplitter() {
	return new OpenNLPSentenceSplitter(sentenceDetectorModel);
    }
}

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

package com.ml_text_utils.nlp.impl.opennlp;

import com.ml_text_utils.nlp.SentenceSplitter;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

import java.util.Arrays;
import java.util.List;

public class OpenNLPSentenceSplitter implements SentenceSplitter {

    private final SentenceModel sentenceDetectorModel;

    public OpenNLPSentenceSplitter(SentenceModel sentenceDetectorModel) {
	this.sentenceDetectorModel = sentenceDetectorModel;
    }

    @Override public List<String> split(String text) {
	return Arrays.asList(new SentenceDetectorME(sentenceDetectorModel).sentDetect(text));
    }

}

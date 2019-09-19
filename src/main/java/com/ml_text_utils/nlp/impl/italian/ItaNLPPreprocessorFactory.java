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

import com.ml_text_utils.nlp.NLPPreprocessor;
import com.ml_text_utils.nlp.NLPPreprocessorFactory;
import com.ml_text_utils.nlp.SentenceSplitter;
import com.ml_text_utils.nlp.impl.NLPPreprocessorImpl;

public class ItaNLPPreprocessorFactory implements NLPPreprocessorFactory {

    private final ItaTokenizerPipelineFactory itaTokenizerPipelineFactory;
    private ItaOpenNLPSentenceSplitterFactory itaOpenNLPSentenceSplitterFactory;

    public ItaNLPPreprocessorFactory() {
	itaOpenNLPSentenceSplitterFactory = new ItaOpenNLPSentenceSplitterFactory();
	itaTokenizerPipelineFactory = new ItaTokenizerPipelineFactory();
    }

    @Override public NLPPreprocessor buildNLPPreprocessor() {

	return new NLPPreprocessorImpl(itaOpenNLPSentenceSplitterFactory.buildSentenceSplitter(), itaTokenizerPipelineFactory.buildTokenizer());
    }

    @Override public SentenceSplitter buildSentenceSplitter() {
	return itaOpenNLPSentenceSplitterFactory.buildSentenceSplitter();
    }
}

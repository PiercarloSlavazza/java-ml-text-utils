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

import com.ml_text_utils.nlp.POSDictionaryFactory;
import com.ml_text_utils.nlp.impl.opennlp.ItaOpenNLPTokenizerPatcher;
import com.ml_text_utils.nlp.impl.opennlp.OpenNLPTokenizer;
import com.ml_text_utils.nlp.impl.opennlp.OpenNLPTokenizerFactory;
import com.ml_text_utils.nlp.impl.opennlp.OpenNLPTokenizerFactoryImpl;

public class ItaOpenNLPTokenizerFactory implements OpenNLPTokenizerFactory {

    private final OpenNLPTokenizerFactoryImpl openNLPTokenizerFactory;

    public ItaOpenNLPTokenizerFactory(POSDictionaryFactory posDictionaryFactory) {
	String openNLPTokenizerModelFileName = "it-token.bin";
	String openNLPPOSTaggerModelFileName = "it-pos-maxent.bin";

	this.openNLPTokenizerFactory = new OpenNLPTokenizerFactoryImpl(posDictionaryFactory,
								       openNLPTokenizerModelFileName,
								       openNLPPOSTaggerModelFileName);
    }

    @Override public OpenNLPTokenizer buildOpenNLPTokenizer() {
	return new ItaOpenNLPTokenizerPatcher(openNLPTokenizerFactory.buildOpenNLPTokenizer());
    }

}

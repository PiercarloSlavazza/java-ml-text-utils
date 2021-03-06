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

package com.ml_text_utils.nlp.impl;

import com.ml_text_utils.nlp.POSDictionaryFactory;
import com.ml_text_utils.nlp.Tokenizer;
import com.ml_text_utils.nlp.TokenizerFactory;
import com.ml_text_utils.nlp.impl.opennlp.OpenNLPTokenizerFactory;
import com.ml_text_utils.nlp.impl.snowball.SnowballStemmerFactory;

public class TokenizerPipelineFactory implements TokenizerFactory {

    private final OpenNLPTokenizerFactory openNLPTokenizerFactory;
    private final SnowballStemmerFactory snowballStemmerFactory;
    private final POSDictionaryFactory posDictionaryFactory;

    public TokenizerPipelineFactory(OpenNLPTokenizerFactory openNLPTokenizerFactory,
				    SnowballStemmerFactory snowballStemmerFactory, POSDictionaryFactory posDictionaryFactory) {
	this.openNLPTokenizerFactory = openNLPTokenizerFactory;
	this.snowballStemmerFactory = snowballStemmerFactory;
	this.posDictionaryFactory = posDictionaryFactory;
    }

    @Override public Tokenizer buildTokenizer() {
	return new TrimmerTokenizer(
			new MinimumLengthTokenizer(
					new LowerCaseTokenizer(
							new StemmerTokenizer(
									snowballStemmerFactory.buildSnowballStemmer(),
									new POSFilteredTokenizer(
											openNLPTokenizerFactory.buildOpenNLPTokenizer(),
											posDictionaryFactory.buildPOSDictionary()
									)
							)
					)
			)
	);
    }
}

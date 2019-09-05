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

import com.ml_text_utils.nlp.POSDictionaryFactory;
import com.ml_text_utils.nlp.impl.opennlp.OpenNLPTokenizer;
import com.ml_text_utils.nlp.impl.opennlp.OpenNLPTokenizerFactory;
import opennlp.tools.postag.POSModel;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.IOException;
import java.io.InputStream;

import static com.ml_text_utils.utils.Classpath.getResourceAsStream;

public class OpenNLPTokenizerFactoryImpl implements OpenNLPTokenizerFactory {

    private final POSDictionaryFactory posDictionaryFactory;
    private final String openNLPTokenizerModelFileName;
    private final String openNLPPOSTaggerModelFileName;

    public OpenNLPTokenizerFactoryImpl(POSDictionaryFactory posDictionaryFactory, String openNLPTokenizerModelFileName,
				String openNLPPOSTaggerModelFileName) {
	this.posDictionaryFactory = posDictionaryFactory;
	this.openNLPTokenizerModelFileName = openNLPTokenizerModelFileName;
	this.openNLPPOSTaggerModelFileName = openNLPPOSTaggerModelFileName;
    }

    @Override public OpenNLPTokenizer buildOpenNLPTokenizer() {

	try (InputStream tokenizerModelStream = getResourceAsStream(openNLPTokenizerModelFileName); InputStream posTaggerModelStream = getResourceAsStream(openNLPPOSTaggerModelFileName)) {

	    TokenizerModel tokenizerModel = new TokenizerModel(tokenizerModelStream);
	    POSModel posModel = new POSModel(posTaggerModelStream);
	    return new OpenNLPTokenizer(tokenizerModel, posModel, posDictionaryFactory.buildPOSDictionary());

	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

}

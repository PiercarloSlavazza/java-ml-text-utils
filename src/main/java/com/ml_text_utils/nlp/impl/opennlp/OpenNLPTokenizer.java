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

import com.codepoetics.protonpack.StreamUtils;
import com.ml_text_utils.nlp.POSDictionary;
import com.ml_text_utils.nlp.PartOfSpeech;
import com.ml_text_utils.nlp.Tokenizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.util.Arrays;
import java.util.stream.Stream;

public class OpenNLPTokenizer implements Tokenizer {

    private final TokenizerModel tokenizerModel;
    private final POSModel posTaggerModel;
    private final POSDictionary posDictionary;

    OpenNLPTokenizer(TokenizerModel tokenizerModel, POSModel posTaggerModel, POSDictionary posDictionary) {
	this.tokenizerModel = tokenizerModel;
	this.posTaggerModel = posTaggerModel;
	this.posDictionary = posDictionary;
    }

    TokenizerModel getTokenizerModel() {
	return tokenizerModel;
    }

    POSModel getPosTaggerModel() {
	return posTaggerModel;
    }

    POSDictionary getPosDictionary() {
	return posDictionary;
    }

    @Override public Stream<OpenNLPToken> tokenize(String text) {

	TokenizerME tokenizer = new TokenizerME(tokenizerModel);
	String[] tokens = tokenizer.tokenize(text);
	Span[] spans = tokenizer.tokenizePos(text);

	POSTaggerME posTagger = new POSTaggerME(posTaggerModel);
	String[] posTags = posTagger.tag(tokens);

	Stream<OpenNLPToken> openNLPTokens =
			StreamUtils.zip(
					Arrays.stream(posTags),
					Arrays.stream(tokens),
					(pos, token) -> new OpenNLPToken(token,
									 new PartOfSpeech(pos, posDictionary.getLabel(pos)))
			);

	return StreamUtils.zip(
			openNLPTokens,
			Stream.of(spans),
			(token, span) -> {
			    token.setStart(span.getStart());
			    token.setEnd(span.getEnd());
			    return token;
			});
    }

}

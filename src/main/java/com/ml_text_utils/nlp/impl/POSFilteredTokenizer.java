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

import com.ml_text_utils.nlp.POSDictionary;
import com.ml_text_utils.nlp.Token;
import com.ml_text_utils.nlp.Tokenizer;

import java.util.stream.Stream;

public class POSFilteredTokenizer implements Tokenizer {

    private final Tokenizer wrapperTokenizer;
    private final POSDictionary posDictionary;

    POSFilteredTokenizer(Tokenizer wrapperTokenizer, POSDictionary posDictionary) {
	this.wrapperTokenizer = wrapperTokenizer;
	this.posDictionary = posDictionary;
    }

    private boolean hasToBeDiscarded(Token token) {
	return posDictionary.isAdverb(token) ||
			posDictionary.isArticulatedPreposition(token) ||
			posDictionary.isBalancedPunctuation(token) ||
			posDictionary.isCardinalNumber(token) ||
			posDictionary.isClauseBoundaryPunctuation(token) ||
			posDictionary.isCoordinateConjunction(token) ||
			posDictionary.isDeterminativeArticle(token) ||
			posDictionary.isIndefiniteDeterminer(token) ||
			posDictionary.isIndeterminativeArticle(token) ||
			posDictionary.isNegationAdverb(token) ||
			posDictionary.isPersonalPronoun(token) ||
			posDictionary.isPredeterminer(token) ||
			posDictionary.isPreposition(token) ||
			posDictionary.isPunctationComma(token) ||
			posDictionary.isRelativePronoun(token) ||
			posDictionary.isSentenceBoundaryPunctuation(token) ||
			posDictionary.isSubordinateConjunction(token) ||
			posDictionary.isModalVerb(token) ||
			posDictionary.isAuxiliaryVerb(token)
			;
    }

    @Override public Stream<? extends Token> tokenize(String text) {
	return wrapperTokenizer.tokenize(text).filter(token -> !hasToBeDiscarded(token));
    }

}

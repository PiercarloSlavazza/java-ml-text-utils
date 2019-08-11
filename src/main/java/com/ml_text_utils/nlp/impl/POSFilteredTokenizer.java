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

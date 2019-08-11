package com.ml_text_utils.nlp.impl;

import com.ml_text_utils.nlp.Token;
import com.ml_text_utils.nlp.Tokenizer;

import java.util.stream.Stream;

public class LowerCaseTokenizer implements Tokenizer {

    private final Tokenizer wrapperTokenizer;

    LowerCaseTokenizer(Tokenizer wrapperTokenizer) {
	this.wrapperTokenizer = wrapperTokenizer;
    }

    private Token toLowerCase(Token token) {
	return new Token(token.getToken().toLowerCase(), token.getPos());
    }

    @Override public Stream<Token> tokenize(String text) {
	return wrapperTokenizer.tokenize(text).map(this::toLowerCase);
    }

}

package com.ml_text_utils.nlp.impl;

import com.ml_text_utils.nlp.Token;
import com.ml_text_utils.nlp.Tokenizer;

import java.util.stream.Stream;

public class MinimumLengthTokenizer implements Tokenizer {

    private final Tokenizer wrapperTokenizer;

    MinimumLengthTokenizer(Tokenizer wrapperTokenizer) {
	this.wrapperTokenizer = wrapperTokenizer;
    }

    @Override public Stream<? extends Token> tokenize(String text) {
	return wrapperTokenizer.tokenize(text).filter(token -> token.getToken().length() > 1);
    }

}

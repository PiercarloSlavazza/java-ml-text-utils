package com.ml_text_utils.nlp.impl;

import com.ml_text_utils.nlp.Token;
import com.ml_text_utils.nlp.Tokenizer;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

public class TrimmerTokenizer implements Tokenizer {

    private static final String CHARS_TO_BE_TRIMMED = "-~/\\";

    private final Tokenizer wrapperTokenizer;

    TrimmerTokenizer(Tokenizer wrapperTokenizer) {
	this.wrapperTokenizer = wrapperTokenizer;
    }

    private Token trim(Token token) {
	return new Token(StringUtils.strip(token.getToken(), CHARS_TO_BE_TRIMMED), token.getPos());
    }

    @Override public Stream<? extends Token> tokenize(String text) {
	return wrapperTokenizer.tokenize(text).map(this::trim).filter(token -> StringUtils.isNotEmpty(token.getToken()));
    }

}

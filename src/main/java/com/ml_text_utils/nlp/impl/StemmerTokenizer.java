package com.ml_text_utils.nlp.impl;

import com.ml_text_utils.nlp.Stemmer;
import com.ml_text_utils.nlp.Token;
import com.ml_text_utils.nlp.Tokenizer;

import java.util.stream.Stream;

public class StemmerTokenizer implements Tokenizer {

    private final Stemmer stemmer;
    private final Tokenizer wrapperTokenizer;

    StemmerTokenizer(Stemmer stemmer, Tokenizer wrapperTokenizer) {
	this.stemmer = stemmer;
	this.wrapperTokenizer = wrapperTokenizer;
    }

    @Override public Stream<Token> tokenize(String text) {
	return wrapperTokenizer.tokenize(text).map(stemmer::stem);
    }

}

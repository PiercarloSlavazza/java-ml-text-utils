package com.ml_text_utils.nlp.impl;

import com.ml_text_utils.nlp.NLPPreprocessor;
import com.ml_text_utils.nlp.SentenceSplitter;
import com.ml_text_utils.nlp.Token;
import com.ml_text_utils.nlp.Tokenizer;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NLPPreprocessorImpl implements NLPPreprocessor {

    private final SentenceSplitter sentenceSplitter;
    private final Tokenizer tokenizer;

    public NLPPreprocessorImpl(SentenceSplitter sentenceSplitter, Tokenizer tokenizer) {
	this.sentenceSplitter = sentenceSplitter;
	this.tokenizer = tokenizer;
    }

    private String joinTokensWithWhiteSpaces(Stream<? extends Token> tokens) {
	return tokens.map(Token::getToken).collect(Collectors.joining(" "));
    }

    @Override public String preprocess(String text) {
	return sentenceSplitter.split(text).stream().
			map(tokenizer::tokenize).
			map(this::joinTokensWithWhiteSpaces).
			filter(StringUtils::isNotEmpty).
			collect(Collectors.joining("\n"));
    }
}

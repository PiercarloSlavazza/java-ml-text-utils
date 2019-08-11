package com.ml_text_utils.nlp;

import java.util.stream.Stream;

public interface Tokenizer {

    Stream<? extends Token> tokenize(String text);

}

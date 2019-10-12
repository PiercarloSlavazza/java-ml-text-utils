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

import com.ml_text_utils.nlp.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NLPPreprocessorPOS implements NLPPreprocessor {

    private final SentenceSplitter sentenceSplitter;
    private final Tokenizer tokenizer;

    public NLPPreprocessorPOS(SentenceSplitter sentenceSplitter, Tokenizer tokenizer) {
	this.sentenceSplitter = sentenceSplitter;
	this.tokenizer = tokenizer;
    }

    private String joinTokensPOSWithWhiteSpaces(Stream<? extends Token> tokens) {
        return tokens.map(Token::getPos).
			filter(Optional::isPresent).
			map(Optional::get).
			map(PartOfSpeech::getId).
			collect(Collectors.joining(" "));
    }

    @Override public String preprocess(String text) {
	return sentenceSplitter.split(text).stream().
			map(tokenizer::tokenize).
			map(this::joinTokensPOSWithWhiteSpaces).
			filter(StringUtils::isNotEmpty).
			collect(Collectors.joining("\n"));
    }
}

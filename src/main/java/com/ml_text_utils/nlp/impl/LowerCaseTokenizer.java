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

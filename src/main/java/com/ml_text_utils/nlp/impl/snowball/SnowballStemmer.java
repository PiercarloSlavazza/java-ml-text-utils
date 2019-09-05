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

package com.ml_text_utils.nlp.impl.snowball;

import com.ml_text_utils.nlp.Stemmer;
import com.ml_text_utils.nlp.Token;
import org.tartarus.snowball.SnowballProgram;

public class SnowballStemmer implements Stemmer {

    private final SnowballProgramFactory snowballStemmerFactory;

    public SnowballStemmer(SnowballProgramFactory snowballStemmerFactory) {
	this.snowballStemmerFactory = snowballStemmerFactory;
    }

    @Override public Token stem(Token token) {

	SnowballProgram snowballStemmer = snowballStemmerFactory.buildSnowballProgram();
	snowballStemmer.setCurrent(token.getToken());
	snowballStemmer.stem();

	return new Token(snowballStemmer.getCurrent(), token.getPos());
    }

}

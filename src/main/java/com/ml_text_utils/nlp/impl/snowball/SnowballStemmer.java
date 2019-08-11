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

package com.ml_text_utils.nlp.impl.italian;

import com.ml_text_utils.nlp.impl.snowball.SnowballStemmer;
import com.ml_text_utils.nlp.impl.snowball.SnowballStemmerFactory;
import org.tartarus.snowball.ext.ItalianStemmer;

public class ItaSnowballStemmerFactory implements SnowballStemmerFactory {

    @Override public SnowballStemmer buildSnowballStemmer() {
	return new SnowballStemmer(ItalianStemmer::new);
    }

}

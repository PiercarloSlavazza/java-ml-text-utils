package com.ml_text_utils.nlp.impl.italian;

import com.ml_text_utils.nlp.Tokenizer;
import com.ml_text_utils.nlp.TokenizerFactory;
import com.ml_text_utils.nlp.impl.TokenizerPipelineFactory;

public class ItaTokenizerPipelineFactory implements TokenizerFactory {

    private final TokenizerPipelineFactory tokenizerPipelineFactory;

    ItaTokenizerPipelineFactory() {
        ItaPOSDictionaryFactory posDictionaryFactory = new ItaPOSDictionaryFactory();
        tokenizerPipelineFactory = new TokenizerPipelineFactory(new ItaOpenNLPTokenizerFactory(posDictionaryFactory), new ItaSnowballStemmerFactory(), posDictionaryFactory);
    }

    @Override public Tokenizer buildTokenizer() {
	return tokenizerPipelineFactory.buildTokenizer();
    }

}

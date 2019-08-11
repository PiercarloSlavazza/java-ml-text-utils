package com.ml_text_utils.nlp.impl;

import com.ml_text_utils.nlp.POSDictionaryFactory;
import com.ml_text_utils.nlp.Tokenizer;
import com.ml_text_utils.nlp.TokenizerFactory;
import com.ml_text_utils.nlp.impl.opennlp.OpenNLPTokenizerFactory;
import com.ml_text_utils.nlp.impl.snowball.SnowballStemmerFactory;

public class TokenizerPipelineFactory implements TokenizerFactory {

    private final OpenNLPTokenizerFactory openNLPTokenizerFactory;
    private final SnowballStemmerFactory snowballStemmerFactory;
    private final POSDictionaryFactory posDictionaryFactory;

    public TokenizerPipelineFactory(OpenNLPTokenizerFactory openNLPTokenizerFactory,
				    SnowballStemmerFactory snowballStemmerFactory, POSDictionaryFactory posDictionaryFactory) {
	this.openNLPTokenizerFactory = openNLPTokenizerFactory;
	this.snowballStemmerFactory = snowballStemmerFactory;
	this.posDictionaryFactory = posDictionaryFactory;
    }

    @Override public Tokenizer buildTokenizer() {
	return new TrimmerTokenizer(
			new MinimumLengthTokenizer(
					new LowerCaseTokenizer(
							new StemmerTokenizer(
									snowballStemmerFactory.buildSnowballStemmer(),
									new POSFilteredTokenizer(
											openNLPTokenizerFactory.buildOpenNLPTokenizer(),
											posDictionaryFactory.buildPOSDictionary()
									)
							)
					)
			)
	);
    }
}

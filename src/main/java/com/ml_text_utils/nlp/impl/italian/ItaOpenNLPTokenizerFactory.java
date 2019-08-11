package com.ml_text_utils.nlp.impl.italian;

import com.ml_text_utils.nlp.POSDictionaryFactory;
import com.ml_text_utils.nlp.impl.opennlp.ItaOpenNLPTokenizerPatcher;
import com.ml_text_utils.nlp.impl.opennlp.OpenNLPTokenizer;
import com.ml_text_utils.nlp.impl.opennlp.OpenNLPTokenizerFactory;
import com.ml_text_utils.nlp.impl.opennlp.OpenNLPTokenizerFactoryImpl;

public class ItaOpenNLPTokenizerFactory implements OpenNLPTokenizerFactory {

    private final OpenNLPTokenizerFactoryImpl openNLPTokenizerFactory;

    public ItaOpenNLPTokenizerFactory(POSDictionaryFactory posDictionaryFactory) {
	String openNLPTokenizerModelFileName = "it-token.bin";
	String openNLPPOSTaggerModelFileName = "it-pos-maxent.bin";

	this.openNLPTokenizerFactory = new OpenNLPTokenizerFactoryImpl(posDictionaryFactory,
								       openNLPTokenizerModelFileName,
								       openNLPPOSTaggerModelFileName);
    }

    @Override public OpenNLPTokenizer buildOpenNLPTokenizer() {
	return new ItaOpenNLPTokenizerPatcher(openNLPTokenizerFactory.buildOpenNLPTokenizer());
    }

}

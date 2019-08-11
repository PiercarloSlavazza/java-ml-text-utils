package com.ml_text_utils.nlp.impl.opennlp;

import com.ml_text_utils.nlp.POSDictionaryFactory;
import com.ml_text_utils.nlp.impl.opennlp.OpenNLPTokenizer;
import com.ml_text_utils.nlp.impl.opennlp.OpenNLPTokenizerFactory;
import opennlp.tools.postag.POSModel;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.IOException;
import java.io.InputStream;

import static com.ml_text_utils.utils.Classpath.getResourceAsStream;

public class OpenNLPTokenizerFactoryImpl implements OpenNLPTokenizerFactory {

    private final POSDictionaryFactory posDictionaryFactory;
    private final String openNLPTokenizerModelFileName;
    private final String openNLPPOSTaggerModelFileName;

    public OpenNLPTokenizerFactoryImpl(POSDictionaryFactory posDictionaryFactory, String openNLPTokenizerModelFileName,
				String openNLPPOSTaggerModelFileName) {
	this.posDictionaryFactory = posDictionaryFactory;
	this.openNLPTokenizerModelFileName = openNLPTokenizerModelFileName;
	this.openNLPPOSTaggerModelFileName = openNLPPOSTaggerModelFileName;
    }

    @Override public OpenNLPTokenizer buildOpenNLPTokenizer() {

	try (InputStream tokenizerModelStream = getResourceAsStream(openNLPTokenizerModelFileName); InputStream posTaggerModelStream = getResourceAsStream(openNLPPOSTaggerModelFileName)) {

	    TokenizerModel tokenizerModel = new TokenizerModel(tokenizerModelStream);
	    POSModel posModel = new POSModel(posTaggerModelStream);
	    return new OpenNLPTokenizer(tokenizerModel, posModel, posDictionaryFactory.buildPOSDictionary());

	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

}

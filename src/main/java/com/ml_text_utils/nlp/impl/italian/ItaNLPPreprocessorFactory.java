package com.ml_text_utils.nlp.impl.italian;

import com.ml_text_utils.nlp.NLPPreprocessor;
import com.ml_text_utils.nlp.NLPPreprocessorFactory;
import com.ml_text_utils.nlp.impl.NLPPreprocessorImpl;

public class ItaNLPPreprocessorFactory implements NLPPreprocessorFactory {

    private final ItaTokenizerPipelineFactory itaTokenizerPipelineFactory;
    private ItaOpenNLPSentenceSplitterFactory itaOpenNLPSentenceSplitterFactory;

    public ItaNLPPreprocessorFactory() {
	itaOpenNLPSentenceSplitterFactory = new ItaOpenNLPSentenceSplitterFactory();
	itaTokenizerPipelineFactory = new ItaTokenizerPipelineFactory();
    }

    @Override public NLPPreprocessor buildNLPPreprocessor() {

	return new NLPPreprocessorImpl(itaOpenNLPSentenceSplitterFactory.buildSentenceSplitter(), itaTokenizerPipelineFactory.buildTokenizer());
    }
}

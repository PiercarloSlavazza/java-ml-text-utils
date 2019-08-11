package com.ml_text_utils.nlp.impl.opennlp;

import com.ml_text_utils.nlp.SentenceSplitter;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

import java.util.Arrays;
import java.util.List;

public class OpenNLPSentenceSplitter implements SentenceSplitter {

    private final SentenceModel sentenceDetectorModel;

    public OpenNLPSentenceSplitter(SentenceModel sentenceDetectorModel) {
	this.sentenceDetectorModel = sentenceDetectorModel;
    }

    @Override public List<String> split(String text) {
	return Arrays.asList(new SentenceDetectorME(sentenceDetectorModel).sentDetect(text));
    }

}

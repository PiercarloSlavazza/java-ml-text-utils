package com.ml_text_utils.nlp.impl.italian;

import com.ml_text_utils.nlp.SentenceSplitter;
import com.ml_text_utils.nlp.SentenceSplitterFactory;
import com.ml_text_utils.nlp.impl.opennlp.OpenNLPSentenceSplitter;
import opennlp.tools.sentdetect.SentenceModel;

import java.io.IOException;
import java.io.InputStream;

import static com.ml_text_utils.utils.Classpath.getResourceAsStream;

public class ItaOpenNLPSentenceSplitterFactory implements SentenceSplitterFactory {

    private final SentenceModel sentenceDetectorModel;

    ItaOpenNLPSentenceSplitterFactory() {
	String openNLPSentenceSplitterModelFileName = "it-sent.bin";

	try (InputStream sentenceSplitterModelStream = getResourceAsStream(openNLPSentenceSplitterModelFileName)) {
	    sentenceDetectorModel = new SentenceModel(sentenceSplitterModelStream);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override public SentenceSplitter buildSentenceSplitter() {
	return new OpenNLPSentenceSplitter(sentenceDetectorModel);
    }
}

package com.ml_text_utils.nlp.impl.opennlp;

import com.ml_text_utils.nlp.PartOfSpeech;
import com.ml_text_utils.nlp.Token;

class OpenNLPToken extends Token {

    OpenNLPToken(String token, PartOfSpeech pos, int start, int end) {
	super(token, pos, start, end);
    }

    OpenNLPToken(String token, PartOfSpeech pos) {
	super(token, pos);
    }

    void setStart(int start) {
	super.start = start;
    }

    void setEnd(int end) {
	super.end = end;
    }
}

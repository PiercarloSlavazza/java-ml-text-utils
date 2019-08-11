package com.ml_text_utils.nlp.impl.opennlp;

import com.codepoetics.protonpack.StreamUtils;
import com.ml_text_utils.nlp.POSDictionary;
import com.ml_text_utils.nlp.PartOfSpeech;
import com.ml_text_utils.nlp.Tokenizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.util.Arrays;
import java.util.stream.Stream;

public class OpenNLPTokenizer implements Tokenizer {

    private final TokenizerModel tokenizerModel;
    private final POSModel posTaggerModel;
    private final POSDictionary posDictionary;

    OpenNLPTokenizer(TokenizerModel tokenizerModel, POSModel posTaggerModel, POSDictionary posDictionary) {
	this.tokenizerModel = tokenizerModel;
	this.posTaggerModel = posTaggerModel;
	this.posDictionary = posDictionary;
    }

    TokenizerModel getTokenizerModel() {
	return tokenizerModel;
    }

    POSModel getPosTaggerModel() {
	return posTaggerModel;
    }

    POSDictionary getPosDictionary() {
	return posDictionary;
    }

    @Override public Stream<OpenNLPToken> tokenize(String text) {

	TokenizerME tokenizer = new TokenizerME(tokenizerModel);
	String[] tokens = tokenizer.tokenize(text);
	Span[] spans = tokenizer.tokenizePos(text);

	POSTaggerME posTagger = new POSTaggerME(posTaggerModel);
	String[] posTags = posTagger.tag(tokens);

	Stream<OpenNLPToken> openNLPTokens =
			StreamUtils.zip(
					Arrays.stream(posTags),
					Arrays.stream(tokens),
					(pos, token) -> new OpenNLPToken(token,
									 new PartOfSpeech(pos, posDictionary.getLabel(pos)))
			);

	return StreamUtils.zip(
			openNLPTokens,
			Stream.of(spans),
			(token, span) -> {
			    token.setStart(span.getStart());
			    token.setEnd(span.getEnd());
			    return token;
			});
    }

}

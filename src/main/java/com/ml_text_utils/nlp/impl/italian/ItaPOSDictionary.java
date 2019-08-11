package com.ml_text_utils.nlp.impl.italian;

import com.ml_text_utils.nlp.POSDictionary;
import com.ml_text_utils.nlp.PartOfSpeech;
import com.ml_text_utils.nlp.Token;

import java.util.Map;
import java.util.Optional;

public class ItaPOSDictionary implements POSDictionary {

    private static final String ADVERB = "B";
    private static final String ARTICULATED_PREPOSITION = "EA";
    private static final String AUXILIARY_VERB = "VA";
    private static final String BALANCED_PUNCTUATION = "FB";
    private static final String CARDINAL_NUMBER = "N";
    private static final String CLAUSE_BOUNDARY_PUNCTUATION = "FC";
    private static final String COORDINATE_CONJUNCTION = "CC";
    private static final String DETERMINATIVE_ARTICLE = "RD";
    private static final String INDEFINITE_DETERMINER = "DI";
    private static final String INDETERMINATIVE_ARTICLE = "RI";
    private static final String MODAL_VERB = "VM";
    private static final String NEGATION_ADVERB = "BN";
    private static final String PERSONAL_PRONOUN = "PE";
    private static final String PLURAL_ADJECTIVE = "Ap";
    private static final String PREDETERMINER = "T";
    private static final String PREPOSITION = "E";
    private static final String PROPER_NOUN = "SP";
    private static final String PUNCTATION_COMMA = "FF";
    private static final String RELATIVE_PRONOUN = "PR";
    private static final String SENTENCE_BOUNDARY_PUNCTUATION = "FS";
    private static final String SINGULAR_ADJECTIVE = "As";
    private static final String SUBORDINATE_CONJUNCTION = "CS";

    private final Map<String, String> posDictionary;

    ItaPOSDictionary(Map<String, String> posDictionary) {
	this.posDictionary = posDictionary;
    }

    @Override
    public Optional<String> getLabel(String posTag) {
	return Optional.ofNullable(posDictionary.get(posTag));
    }

    private boolean hasPOS(Token token, String posId) {
        return token.getPos().map(PartOfSpeech::getId).map(posId::equals).orElse(false);
    }

    @Override public boolean isAdverb(Token token) {
	return hasPOS(token, ADVERB);
    }

    @Override public boolean isArticulatedPreposition(Token token) {
	return hasPOS(token, ARTICULATED_PREPOSITION);
    }

    @Override public boolean isAuxiliaryVerb(Token token) {
	return hasPOS(token, AUXILIARY_VERB);
    }

    @Override public boolean isBalancedPunctuation(Token token) {
	return hasPOS(token, BALANCED_PUNCTUATION);
    }

    @Override public boolean isCardinalNumber(Token token) {
	return hasPOS(token, CARDINAL_NUMBER);
    }

    @Override public boolean isClauseBoundaryPunctuation(Token token) {
	return hasPOS(token, CLAUSE_BOUNDARY_PUNCTUATION);
    }

    @Override public boolean isCoordinateConjunction(Token token) {
	return hasPOS(token, COORDINATE_CONJUNCTION);
    }

    @Override public boolean isDeterminativeArticle(Token token) {
	return hasPOS(token, DETERMINATIVE_ARTICLE);
    }

    @Override public boolean isIndefiniteDeterminer(Token token) {
	return hasPOS(token, INDEFINITE_DETERMINER);
    }

    @Override public boolean isIndeterminativeArticle(Token token) {
	return hasPOS(token, INDETERMINATIVE_ARTICLE);
    }

    @Override public boolean isModalVerb(Token token) {
	return hasPOS(token, MODAL_VERB);
    }

    @Override public boolean isNegationAdverb(Token token) {
	return hasPOS(token, NEGATION_ADVERB);
    }

    @Override public boolean isPersonalPronoun(Token token) {
	return hasPOS(token, PERSONAL_PRONOUN);
    }

    @Override public boolean isPluralAdjective(Token token) {
	return hasPOS(token, PLURAL_ADJECTIVE);
    }

    @Override public boolean isPredeterminer(Token token) {
	return hasPOS(token, PREDETERMINER);
    }

    @Override public boolean isPreposition(Token token) {
	return hasPOS(token, PREPOSITION);
    }

    @Override public boolean isProperNoun(Token token) {
	return hasPOS(token, PROPER_NOUN);
    }

    @Override public boolean isPunctationComma(Token token) {
	return hasPOS(token, PUNCTATION_COMMA);
    }

    @Override public boolean isRelativePronoun(Token token) {
	return hasPOS(token, RELATIVE_PRONOUN);
    }

    @Override public boolean isSentenceBoundaryPunctuation(Token token) {
	return hasPOS(token, SENTENCE_BOUNDARY_PUNCTUATION);
    }

    @Override public boolean isSingularAdjective(Token token) {
	return hasPOS(token, SINGULAR_ADJECTIVE);
    }

    @Override public boolean isSubordinateConjunction(Token token) {
	return hasPOS(token, SUBORDINATE_CONJUNCTION);
    }

}

package com.ml_text_utils.nlp;

import java.util.Optional;

public interface POSDictionary {

    boolean isAdverb(Token token);
    boolean isArticulatedPreposition(Token token);
    boolean isAuxiliaryVerb(Token token);
    boolean isBalancedPunctuation(Token token);
    boolean isCardinalNumber(Token token);
    boolean isClauseBoundaryPunctuation(Token token);
    boolean isCoordinateConjunction(Token token);
    boolean isDeterminativeArticle(Token token);
    boolean isIndefiniteDeterminer(Token token);
    boolean isIndeterminativeArticle(Token token);
    boolean isModalVerb(Token token);
    boolean isNegationAdverb(Token token);
    boolean isPersonalPronoun(Token token);
    boolean isPluralAdjective(Token token);
    boolean isPredeterminer(Token token);
    boolean isPreposition(Token token);
    boolean isProperNoun(Token token);
    boolean isPunctationComma(Token token);
    boolean isRelativePronoun(Token token);
    boolean isSentenceBoundaryPunctuation(Token token);
    boolean isSingularAdjective(Token token);
    boolean isSubordinateConjunction(Token token);

    Optional<String> getLabel(String posTag);
}

/*
 * Copyright  2019 Piercarlo Slavazza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.ml_text_utils.nlp.impl.italian;

import com.ml_text_utils.nlp.POSDictionary;
import com.ml_text_utils.nlp.PartOfSpeech;
import com.ml_text_utils.nlp.Token;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.asList;

public class ItaPOSDictionary implements POSDictionary {

    private static final String ADVERB = "B";
    private static final String ARTICULATED_PREPOSITION = "EA";
    private static final String AUXILIARY_VERB = "VA";
    private static final String AUXILIARY_VERB_CONDITIONAL_PRESENT_3_PERSON = "VAdp3";
    private static final String AUXILIARY_VERB_CONDITIONAL_PRESENT_OTHER_THAN_3_PERSON = "VAdp";
    private static final String AUXILIARY_VERB_CONJUNCTIVE_IMPERFECT_3_PERSON = "VAci3";
    private static final String AUXILIARY_VERB_CONJUNCTIVE_IMPERFECT_OTHER_THAN_3_PERSON = "VAci";
    private static final String AUXILIARY_VERB_CONJUNCTIVE_PRESENT_3_PERSON = "VAcp3";
    private static final String AUXILIARY_VERB_CONJUNCTIVE_PRESENT_OTHER_THAN_3_PERSON = "VAcp";
    private static final String AUXILIARY_VERB_GERUNDIVE = "VAg";
    private static final String AUXILIARY_VERB_IMPERATIVE = "VAm";
    private static final String AUXILIARY_VERB_INDICATIVE_FUTURE_3_PERSON = "VAif3";
    private static final String AUXILIARY_VERB_INDICATIVE_FUTURE_OTHER_THAN_3_PERSON = "VAif";
    private static final String AUXILIARY_VERB_INDICATIVE_IMPERFECT_3_PERSON = "VAii3";
    private static final String AUXILIARY_VERB_INDICATIVE_IMPERFECT_OTHER_THAN_3_PERSON = "VAii";
    private static final String AUXILIARY_VERB_INDICATIVE_PAST_3_PERSON = "Vis3";
    private static final String AUXILIARY_VERB_INDICATIVE_PAST_OTHER_THAN_3_PERSON = "VAis";
    private static final String AUXILIARY_VERB_INDICATIVE_PRESENT_3_PERSON = "VAip3";
    private static final String AUXILIARY_VERB_INDICATIVE_PRESENT_OTHER_THAN_3_PERSON = "VAip";
    private static final String AUXILIARY_VERB_INFINITE = "VAf";
    private static final String AUXILIARY_VERB_PARTICIPLE = "VAp";
    private static final String BALANCED_PUNCTUATION = "FB";
    private static final String CARDINAL_NUMBER = "N";
    private static final String CLAUSE_BOUNDARY_PUNCTUATION = "FC";
    private static final String CLITIC_PRONOUN = "PC";
    private static final String COORDINATE_CONJUNCTION = "CC";
    private static final String DEMONSTRATIVE_PRONOUN = "PD";
    private static final String DETERMINATIVE_ARTICLE = "RD";
    private static final String INDEFINITE_DETERMINER = "DI";
    private static final String INDEFINITE_PRONOUN = "PI";
    private static final String INDETERMINATIVE_ARTICLE = "RI";
    private static final String INTERROGATIVE_PRONOUN = "PQ";
    private static final String MODAL_VERB = "VM";
    private static final String NEGATION_ADVERB = "BN";
    private static final String PERSONAL_PRONOUN = "PE";
    private static final String PLURAL_ADJECTIVE = "Ap";
    private static final String PLURAL_ORDINAL_NUMBER = "NOp";
    private static final String POSSESSIVE_PRONOUN = "PP";
    private static final String PREDETERMINER = "T";
    private static final String PREPOSITION = "E";
    private static final String PROPER_NOUN = "SP";
    private static final String PUNCTATION_COMMA = "FF";
    private static final String RELATIVE_PRONOUN = "PR";
    private static final String SENTENCE_BOUNDARY_PUNCTUATION = "FS";
    private static final String SINGULAR_ADJECTIVE = "As";
    private static final String SINGULAR_ORDINAL_NUMBER = "NOs";
    private static final String SUBORDINATE_CONJUNCTION = "CS";
    private static final String UNDERSPECIFIED_ORDINAL_NUMBER = "NOn";

    private static final String MODAL_VERB_INDICATIVE_PRESENT_OTHER_THAN_3_PERSON = "VMip";
    private static final String MODAL_VERB_INDICATIVE_PRESENT_3_PERSON = "VMip3";
    private static final String MODAL_VERB_INDICATIVE_IMPERFECT_OTHER_THAN_3_PERSON = "VMii";
    private static final String MODAL_VERB_INDICATIVE_IMPERFECT_3_PERSON = "VMii3";
    private static final String MODAL_VERB_INDICATIVE_PAST_OTHER_THAN_3_PERSON = "VMis";
    private static final String MODAL_VERB_INDICATIVE_PAST_3_PERSON = "VMis3";
    private static final String MODAL_VERB_INDICATIVE_FUTURE_OTHER_THAN_3_PERSON = "VMif";
    private static final String MODAL_VERB_INDICATIVE_FUTURE_3_PERSON = "VMif3";
    private static final String MODAL_VERB_CONJUNCTIVE_PRESENT_OTHER_THAN_3_PERSON = "VMcp";
    private static final String MODAL_VERB_CONJUNCTIVE_PRESENT_3_PERSON = "VMcp3";
    private static final String MODAL_VERB_CONJUNCTIVE_IMPERFECT_OTHER_THAN_3_PERSON = "VMci";
    private static final String MODAL_VERB_CONJUNCTIVE_IMPERFECT_3_PERSON = "VMci3";
    private static final String MODAL_VERB_CONDITIONAL_PRESENT_OTHER_THAN_3_PERSON = "VMdp";
    private static final String MODAL_VERB_CONDITIONAL_PRESENT_3_PERSON = "VMdp3";
    private static final String MODAL_VERB_GERUNDIVE = "VMg";
    private static final String MODAL_VERB_PARTICIPLE = "VMp";
    private static final String MODAL_VERB_INFINITE = "VMf";
    private static final String MODAL_VERB_IMPERATIVE = "VMm";

    private static final List<String> AUXILIARY_VERB_POSes = asList(AUXILIARY_VERB_INDICATIVE_PRESENT_OTHER_THAN_3_PERSON, AUXILIARY_VERB_INDICATIVE_PRESENT_3_PERSON, AUXILIARY_VERB_INDICATIVE_IMPERFECT_OTHER_THAN_3_PERSON, AUXILIARY_VERB_INDICATIVE_IMPERFECT_3_PERSON, AUXILIARY_VERB_INDICATIVE_PAST_OTHER_THAN_3_PERSON, AUXILIARY_VERB_INDICATIVE_PAST_3_PERSON, AUXILIARY_VERB_INDICATIVE_FUTURE_OTHER_THAN_3_PERSON, AUXILIARY_VERB_INDICATIVE_FUTURE_3_PERSON, AUXILIARY_VERB_CONJUNCTIVE_PRESENT_OTHER_THAN_3_PERSON, AUXILIARY_VERB_CONJUNCTIVE_PRESENT_3_PERSON, AUXILIARY_VERB_CONJUNCTIVE_IMPERFECT_OTHER_THAN_3_PERSON, AUXILIARY_VERB_CONJUNCTIVE_IMPERFECT_3_PERSON, AUXILIARY_VERB_CONDITIONAL_PRESENT_OTHER_THAN_3_PERSON, AUXILIARY_VERB_CONDITIONAL_PRESENT_3_PERSON, AUXILIARY_VERB_GERUNDIVE, AUXILIARY_VERB_PARTICIPLE, AUXILIARY_VERB_INFINITE, AUXILIARY_VERB_IMPERATIVE);
    private static final List<String> MODAL_VERB_POSes = asList(MODAL_VERB_INDICATIVE_PRESENT_OTHER_THAN_3_PERSON, MODAL_VERB_INDICATIVE_PRESENT_3_PERSON, MODAL_VERB_INDICATIVE_IMPERFECT_OTHER_THAN_3_PERSON, MODAL_VERB_INDICATIVE_IMPERFECT_3_PERSON, MODAL_VERB_INDICATIVE_PAST_OTHER_THAN_3_PERSON, MODAL_VERB_INDICATIVE_PAST_3_PERSON, MODAL_VERB_INDICATIVE_FUTURE_OTHER_THAN_3_PERSON, MODAL_VERB_INDICATIVE_FUTURE_3_PERSON, MODAL_VERB_CONJUNCTIVE_PRESENT_OTHER_THAN_3_PERSON, MODAL_VERB_CONJUNCTIVE_PRESENT_3_PERSON, MODAL_VERB_CONJUNCTIVE_IMPERFECT_OTHER_THAN_3_PERSON, MODAL_VERB_CONJUNCTIVE_IMPERFECT_3_PERSON, MODAL_VERB_CONDITIONAL_PRESENT_OTHER_THAN_3_PERSON, MODAL_VERB_CONDITIONAL_PRESENT_3_PERSON, MODAL_VERB_GERUNDIVE, MODAL_VERB_PARTICIPLE, MODAL_VERB_INFINITE, MODAL_VERB_IMPERATIVE);
    private static final List<String> NUMBERS_POSes = asList(CARDINAL_NUMBER, PLURAL_ORDINAL_NUMBER, SINGULAR_ORDINAL_NUMBER, UNDERSPECIFIED_ORDINAL_NUMBER);
    private static final List<String> PRONOUN_POSes = asList(CLITIC_PRONOUN, DEMONSTRATIVE_PRONOUN, PERSONAL_PRONOUN, INDEFINITE_PRONOUN, POSSESSIVE_PRONOUN, INTERROGATIVE_PRONOUN, RELATIVE_PRONOUN);

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

    private boolean hasPOS(Token token, List<String> posIds) {
        return token.getPos().
                map(PartOfSpeech::getId).
                map(partOfSpeech -> posIds.stream().anyMatch(partOfSpeech::equals)).
                orElse(false);
    }

    @Override public boolean isAdverb(Token token) {
        return hasPOS(token, ADVERB);
    }

    @Override public boolean isArticulatedPreposition(Token token) {
        return hasPOS(token, ARTICULATED_PREPOSITION);
    }

    @Override public boolean isAuxiliaryVerb(Token token) {
        if (hasPOS(token, AUXILIARY_VERB)) return true;
        return hasPOS(token, AUXILIARY_VERB_POSes);
    }

    @Override public boolean isBalancedPunctuation(Token token) {
        return hasPOS(token, BALANCED_PUNCTUATION);
    }

    @Override public boolean isCardinalNumber(Token token) {
        return hasPOS(token, CARDINAL_NUMBER);
    }

    @Override public boolean isNumber(Token token) {
        return hasPOS(token, PRONOUN_POSes);
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
        if (hasPOS(token, MODAL_VERB)) return true;
        return hasPOS(token, MODAL_VERB_POSes);
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

    @Override public boolean isPronoun(Token token) {
        return hasPOS(token, NUMBERS_POSes);
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

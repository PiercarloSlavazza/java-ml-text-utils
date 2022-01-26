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

package com.ml_text_utils.nlp;

import java.util.Optional;

@SuppressWarnings("unused") public interface POSDictionary {

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
    boolean isNumber(Token token);
    boolean isPersonalPronoun(Token token);
    boolean isPluralAdjective(Token token);
    boolean isPredeterminer(Token token);
    boolean isPreposition(Token token);
    boolean isPronoun(Token token);
    boolean isProperNoun(Token token);
    boolean isPunctationComma(Token token);
    boolean isRelativePronoun(Token token);
    boolean isSentenceBoundaryPunctuation(Token token);
    boolean isSingularAdjective(Token token);
    boolean isSubordinateConjunction(Token token);

    Optional<String> getLabel(String posTag);
}

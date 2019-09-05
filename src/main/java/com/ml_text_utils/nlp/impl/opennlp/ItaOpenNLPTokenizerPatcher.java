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

package com.ml_text_utils.nlp.impl.opennlp;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class TextTransduced {

    private final String text;
    private final Deque<Integer> indexesOfAddedSpaces;
    private final Deque<Integer> indexesOfReplacedCharacters;

    TextTransduced(String text, Deque<Integer> indexesOfAddedSpaces, Deque<Integer> indexesOfReplacedCharacters) {
	super();
	this.text = text;
	this.indexesOfAddedSpaces = indexesOfAddedSpaces;
	this.indexesOfReplacedCharacters = indexesOfReplacedCharacters;
    }

    public String getText() {
	return text;
    }

    Deque<Integer> getIndexesOfAddedSpaces() {
	return indexesOfAddedSpaces;
    }

    Deque<Integer> getIndexesOfReplacedCharacters() {
	return indexesOfReplacedCharacters;
    }

}

class FSMString {

    private final String string;
    private int lookingForCharAtIndex;
    private char lookingForChar;

    FSMString(String string) {
	this.string = string;
	if (StringUtils.isEmpty(string)) throw new RuntimeException("string must be non null and non void");

	reset();
    }

    void reset() {
	lookingForCharAtIndex = 0;
	lookingForChar = string.charAt(lookingForCharAtIndex);
    }

    boolean match(char c) {
	if (lookingForChar != c) {
	    reset();

	    /*
	      we check once again wrt the resetted pattern because the character that 
	      failed for the current position might match the first character of the pattern
	     */
	    if (lookingForChar != c) return false;
	}

	if (lookingForCharAtIndex == string.length() - 1) {
	    reset();
	    return true;
	}

	lookingForChar = string.charAt(++lookingForCharAtIndex);
	return false;
    }
}

public class ItaOpenNLPTokenizerPatcher extends OpenNLPTokenizer {

    @SuppressWarnings("serial")
    private static final Set<String> DEFAULT_STRINGS_NEEDING_TRAILING_SPACE = new HashSet<String>() {{
	add("EURO");
	add("Euro");
	add("€");
	add("\n-");
    }};
    private static final List<Character> CHARS_THAT_NEED_TRAILING_SPACE = Arrays.asList('(', '“', ',');

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static final List<Pair<Character, Character>> CHARS_THAT_NEEDS_TO_BE_REPLACED =
		    Arrays.asList(new ImmutablePair('’', '\''),
				  new ImmutablePair('”', '\"'));

    private static final Map<Character, Character> REPLACE_TO = CHARS_THAT_NEEDS_TO_BE_REPLACED.
		    stream().
		    collect(Collectors.toMap(Pair::getLeft, Pair::getRight));

    private static final Map<Character, Character> REVERT_FROM = CHARS_THAT_NEEDS_TO_BE_REPLACED.
		    stream().
		    collect(Collectors.toMap(Pair::getRight, Pair::getLeft));

    private final Set<String> stringsNeedingTrailingSpaces;

    private final OpenNLPTokenizer openNLPTokenizer;

    public ItaOpenNLPTokenizerPatcher(OpenNLPTokenizer tokenizer) {
	super(tokenizer.getTokenizerModel(), tokenizer.getPosTaggerModel(), tokenizer.getPosDictionary());
	this.openNLPTokenizer = tokenizer;
	this.stringsNeedingTrailingSpaces = new HashSet<String>() {{
	    addAll(DEFAULT_STRINGS_NEEDING_TRAILING_SPACE);
	}};
    }

    private TextTransduced transduceText(String text) {

	Deque<Integer> indexesOfAddedSpaces = new ArrayDeque<>();
	Deque<Integer> indexesOfReplacedCharacters = new ArrayDeque<>();
	StringBuilder textTransduced = new StringBuilder();

	final List<FSMString> fsmStringsNeedingTrailingSpaces = stringsNeedingTrailingSpaces.stream().map(FSMString::new).collect(Collectors.toList());

	AtomicInteger indexOfTransducedText = new AtomicInteger(-1);
	for (int i = 0; i < text.length(); i++){
	    indexOfTransducedText.incrementAndGet();
	    char character = text.charAt(i);

	    Character possiblyTransducedCharacter = Optional.ofNullable(REPLACE_TO.get(character)).
			    map(transducedCharacter -> {
				indexesOfReplacedCharacters.addLast(indexOfTransducedText.get());
				return transducedCharacter;
			    }).orElse(character);
	    textTransduced.append(possiblyTransducedCharacter);

	    if (CHARS_THAT_NEED_TRAILING_SPACE.contains(character)) {
		fsmStringsNeedingTrailingSpaces.forEach(FSMString::reset);
		textTransduced.append(" ");
		indexesOfAddedSpaces.addLast(indexOfTransducedText.incrementAndGet());
		continue;
	    }

	    if (fsmStringsNeedingTrailingSpaces.stream().noneMatch(fsmString -> fsmString.match(character))) continue;

	    textTransduced.append(" ");
	    indexesOfAddedSpaces.addLast(indexOfTransducedText.incrementAndGet());
	}

	return new TextTransduced(textTransduced.toString(), indexesOfAddedSpaces, indexesOfReplacedCharacters);
    }

    private OpenNLPToken revertReplacements(OpenNLPToken token, final Deque<Integer> indexesOfReplacedCharacters) {
	StringBuilder tokenWithRevertedCharacters = new StringBuilder();

	String tokenText = token.getToken();
	long offset = token.getStart().orElseThrow(RuntimeException::new);
	for (int i = 0; i < tokenText.length(); i++) {
	    char character = tokenText.charAt(i);
	    long characterIndex = offset  + i;
	    Character possiblyRevertedCharacter = Optional.ofNullable(REVERT_FROM.get(character)).
			    filter(c -> !indexesOfReplacedCharacters.isEmpty()).
			    filter(c -> {
				Boolean characterWasTransduced = characterIndex == (long) indexesOfReplacedCharacters.peek();
				if (characterWasTransduced) indexesOfReplacedCharacters.pop();
				return characterWasTransduced;
			    }).
			    orElse(character);
	    tokenWithRevertedCharacters.append(possiblyRevertedCharacter);
	}

	return new OpenNLPToken(tokenWithRevertedCharacters.toString(),
				token.getPos().orElseThrow(RuntimeException::new),
				token.getStart().orElseThrow(RuntimeException::new),
				token.getEnd().orElseThrow(RuntimeException::new));
    }

    @Override
    public Stream<OpenNLPToken> tokenize(String text) {

	TextTransduced textTransduced = transduceText(text);

	Deque<Integer> indexesOfAddedSpaces = textTransduced.getIndexesOfAddedSpaces();
	AtomicInteger reconciliationOffset = new AtomicInteger(0);

	return openNLPTokenizer.tokenize(textTransduced.getText()).
			map(token -> revertReplacements(token, textTransduced.getIndexesOfReplacedCharacters())).
			map(token -> {
			    OpenNLPToken reconciledToken = new OpenNLPToken(token.getToken(),
									    token.getPos().orElseThrow(RuntimeException::new),
									    token.getStart().orElseThrow(RuntimeException::new) - reconciliationOffset.get(),
									    token.getEnd().orElseThrow(RuntimeException::new) - reconciliationOffset.get());

			    Optional.ofNullable(indexesOfAddedSpaces.peekFirst()).
					    filter(indexOfFirstSpaceNotConsumed -> indexOfFirstSpaceNotConsumed.equals(token.getEnd().orElseThrow(RuntimeException::new))).
					    ifPresent(i -> {
						reconciliationOffset.incrementAndGet();
						indexesOfAddedSpaces.pop();
					    });
			    return reconciledToken;
			});
    }

}

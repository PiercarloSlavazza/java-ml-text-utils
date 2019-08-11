package com.ml_text_utils.nlp;

import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType") public class Token {

    private String token;
    private PartOfSpeech pos;
    protected Integer start;
    protected Integer end;

    public Token(String token, PartOfSpeech pos, Integer start, Integer end) {
        this(token, Optional.of(pos), Optional.of(start), Optional.of(end));
    }

    public Token(String token, Optional<PartOfSpeech> pos) {
        this(token, pos, Optional.empty(), Optional.empty());
    }

    public Token(String token, PartOfSpeech pos) {
        this(token, Optional.of(pos), Optional.empty(), Optional.empty());
    }

    private Token(String token,
		  Optional<PartOfSpeech> pos,
		  Optional<Integer> start,
		  Optional<Integer> end) {
	this.token = token;
	this.pos = pos.orElse(null);
	this.start = start.orElse(null);
	this.end = end.orElse(null);
    }

    public Optional<Integer> getStart() {
	return Optional.ofNullable(start);
    }

    public Optional<Integer> getEnd() {
	return Optional.ofNullable(end);
    }

    public String getToken() {
	return token;
    }

    public Optional<PartOfSpeech> getPos() {
	return Optional.ofNullable(pos);
    }

    @Override public String toString() {
	return "Token{" +
			"token='" + token + '\'' +
			", pos=" + pos +
			", start=" + start +
			", end=" + end +
			'}';
    }

    @Override public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof Token))
	    return false;
	Token token1 = (Token) o;
	return Objects.equals(token, token1.token) &&
			Objects.equals(pos, token1.pos) &&
			Objects.equals(start, token1.start) &&
			Objects.equals(end, token1.end);
    }

    @Override public int hashCode() {
	return Objects.hash(token, pos, start, end);
    }
}

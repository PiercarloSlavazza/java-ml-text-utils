package com.ml_text_utils.nlp;

import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType") public class PartOfSpeech {

    private final String id;
    private final String label;

    public PartOfSpeech(String id, Optional<String> label) {
	this.id = id;
	this.label = label.orElse(null);
    }

    public String getId() {
	return id;
    }

    public Optional<String> getLabel() {
	return Optional.ofNullable(label);
    }

    @Override public String toString() {
	return "PartOfSpeech{" +
			"id='" + id + '\'' +
			", label='" + label + '\'' +
			'}';
    }

    @Override public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof PartOfSpeech))
	    return false;
	PartOfSpeech that = (PartOfSpeech) o;
	return Objects.equals(id, that.id) &&
			Objects.equals(label, that.label);
    }

    @Override public int hashCode() {
	return Objects.hash(id, label);
    }
}

package com.ml_text_utils.corpus;

import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType") public class CorpusConstraints {

    private final Integer minDocsPerClass;
    private final Integer maxDocsPerClass;

    public CorpusConstraints(Integer minDocsPerClass, Optional<Integer> maxDocsPerClass) {
	this.minDocsPerClass = minDocsPerClass;
	this.maxDocsPerClass = maxDocsPerClass.orElse(null);
    }

    Integer getMinDocsPerClass() {
	return minDocsPerClass;
    }

    Optional<Integer> getMaxDocsPerClass() {
	return Optional.ofNullable(maxDocsPerClass);
    }

    @Override public String toString() {
	return "CorpusConstraints{" +
			"minDocsPerClass=" + minDocsPerClass +
			", maxDocsPerClass=" + maxDocsPerClass +
			'}';
    }

    @Override public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof CorpusConstraints))
	    return false;
	CorpusConstraints that = (CorpusConstraints) o;
	return Objects.equals(minDocsPerClass, that.minDocsPerClass) &&
			Objects.equals(maxDocsPerClass, that.maxDocsPerClass);
    }

    @Override public int hashCode() {
	return Objects.hash(minDocsPerClass, maxDocsPerClass);
    }
}

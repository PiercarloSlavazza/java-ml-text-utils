package com.ml_text_utils.features.tfidf;

import java.util.Objects;

public class TfIdf {

    private final String term;
    private final double tfIdf;

    public TfIdf(String term, double tfIdf) {
	this.term = term;
	this.tfIdf = tfIdf;
    }

    public String getTerm() {
	return term;
    }

    public double getTfIdf() {
	return tfIdf;
    }

    @Override public String toString() {
	return "TfIdf{" +
			"term='" + term + '\'' +
			", tfIdf=" + tfIdf +
			'}';
    }

    @Override public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof TfIdf))
	    return false;
	TfIdf tfIdf1 = (TfIdf) o;
	return Double.compare(tfIdf1.tfIdf, tfIdf) == 0 &&
			Objects.equals(term, tfIdf1.term);
    }

    @Override public int hashCode() {
	return Objects.hash(term, tfIdf);
    }
}

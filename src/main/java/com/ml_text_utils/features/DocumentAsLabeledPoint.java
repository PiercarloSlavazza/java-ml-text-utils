package com.ml_text_utils.features;

import java.util.Objects;

public class DocumentAsLabeledPoint {

    private final String documentId;
    private final LabeledPoint labeledPoint;

    public DocumentAsLabeledPoint(String documentId, LabeledPoint labeledPoint) {
	this.documentId = documentId;
	this.labeledPoint = labeledPoint;
    }

    public String getDocumentId() {
	return documentId;
    }

    public LabeledPoint getLabeledPoint() {
	return labeledPoint;
    }

    @Override public String toString() {
	return "DocumentAsLabeledPoint{" +
			"documentId='" + documentId + '\'' +
			", labeledPoint=" + labeledPoint +
			'}';
    }

    @Override public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof DocumentAsLabeledPoint))
	    return false;
	DocumentAsLabeledPoint that = (DocumentAsLabeledPoint) o;
	return Objects.equals(documentId, that.documentId) &&
			Objects.equals(labeledPoint, that.labeledPoint);
    }

    @Override public int hashCode() {
	return Objects.hash(documentId, labeledPoint);
    }
}

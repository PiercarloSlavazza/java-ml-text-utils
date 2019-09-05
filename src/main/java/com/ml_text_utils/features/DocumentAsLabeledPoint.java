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

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

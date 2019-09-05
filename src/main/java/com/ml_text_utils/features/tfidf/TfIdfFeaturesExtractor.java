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

import com.ml_text_utils.features.DocumentAsLabeledPoint;
import com.ml_text_utils.features.FeaturesExtractor;
import com.ml_text_utils.features.LabeledPoint;
import com.ml_text_utils.labels.RealValueLabelsDictionary;
import com.ml_text_utils.labels.RealValuedLabel;
import com.ml_text_utils.utils.Vector;
import com.ml_text_utils.utils.Vectors;

import java.util.Optional;
import java.util.Set;

public class TfIdfFeaturesExtractor implements FeaturesExtractor {

    private final TermsDictionary termsDictionary;
    private final CorpusTermsStatistics corpusTermsStatistics;
    private final RealValueLabelsDictionary realValueLabelsDictionary;

    public TfIdfFeaturesExtractor(TermsDictionary termsDictionary, CorpusTermsStatistics corpusTermsStatistics,
				  RealValueLabelsDictionary realValueLabelsDictionary) {
	this.termsDictionary = termsDictionary;
	this.corpusTermsStatistics = corpusTermsStatistics;
	this.realValueLabelsDictionary = realValueLabelsDictionary;
    }

    @Override public DocumentAsLabeledPoint extractFeaturesFromDocumentInCorpus(String documentId, String classLabel) {
	Set<TfIdf> tfIdfs = corpusTermsStatistics.getTfIdfsForDocumentInCorpus(documentId);

	double[] features = tfIdfs.stream().
			filter(tfIdf -> termsDictionary.getIndex(tfIdf.getTerm()).isPresent()).
			map(TfIdf::getTfIdf).
			mapToDouble(d -> d).toArray();

	int[] indexes = tfIdfs.stream().map(TfIdf::getTerm).
			map(termsDictionary::getIndex).
			filter(Optional::isPresent).
			map(Optional::get).
			mapToInt(i -> i).
			toArray();

	assert indexes.length == features.length;

	Vector featuresVector = Vectors.sparse(termsDictionary.getSize(), indexes, features);
	RealValuedLabel realValuedLabel = realValueLabelsDictionary.getRealValueLabelForClass(classLabel);

	LabeledPoint labeledPoint = new LabeledPoint(realValuedLabel.getRealValue(), featuresVector);
	return new DocumentAsLabeledPoint(documentId, labeledPoint);
    }

}

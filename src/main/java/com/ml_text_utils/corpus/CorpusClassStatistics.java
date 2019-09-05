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

package com.ml_text_utils.corpus;

import com.ml_text_utils.ClassLabel;

import java.util.Objects;

public class CorpusClassStatistics {

    private final Integer trainingDocumentsCount;
    private final Integer testDocumentsCount;
    private final ClassLabel classLabel;

    CorpusClassStatistics(Integer trainingDocumentsCount, Integer testDocumentsCount, ClassLabel classLabel) {
	this.trainingDocumentsCount = trainingDocumentsCount;
	this.testDocumentsCount = testDocumentsCount;
	this.classLabel = classLabel;
    }

    @SuppressWarnings("WeakerAccess") public Integer getTrainingDocumentsCount() {
	return trainingDocumentsCount;
    }

    @SuppressWarnings("WeakerAccess") public Integer getTestDocumentsCount() {
	return testDocumentsCount;
    }

    @SuppressWarnings("WeakerAccess") public ClassLabel getClassLabel() {
	return classLabel;
    }

    @Override public String toString() {
	return "CorpusClassStatistics{" +
			"trainingDocumentsCount=" + trainingDocumentsCount +
			", testDocumentsCount=" + testDocumentsCount +
			", classLabel=" + classLabel +
			'}';
    }

    @Override public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof CorpusClassStatistics))
	    return false;
	CorpusClassStatistics that = (CorpusClassStatistics) o;
	return Objects.equals(trainingDocumentsCount, that.trainingDocumentsCount) &&
			Objects.equals(testDocumentsCount, that.testDocumentsCount) &&
			Objects.equals(classLabel, that.classLabel);
    }

    @Override public int hashCode() {
	return Objects.hash(trainingDocumentsCount, testDocumentsCount, classLabel);
    }
}

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

import java.util.List;
import java.util.Objects;

public class CorpusStatistics {

    private final Integer trainingDocumentsCount;
    private final Integer testDocumentsCount;
    private final List<CorpusClassStatistics> corpusClassesStatistics;

    CorpusStatistics(Integer trainingDocumentsCount, Integer testDocumentsCount,
		     List<CorpusClassStatistics> corpusClassesStatistics) {
	this.trainingDocumentsCount = trainingDocumentsCount;
	this.testDocumentsCount = testDocumentsCount;
	this.corpusClassesStatistics = corpusClassesStatistics;
    }

    public Integer getTrainingDocumentsCount() {
	return trainingDocumentsCount;
    }

    public Integer getTestDocumentsCount() {
	return testDocumentsCount;
    }

    public List<CorpusClassStatistics> getCorpusClassesStatistics() {
	return corpusClassesStatistics;
    }

    @Override public String toString() {
	return "CorpusStatistics{" +
			"trainingDocumentsCount=" + trainingDocumentsCount +
			", testDocumentsCount=" + testDocumentsCount +
			", corpusClassesStatistics=" + corpusClassesStatistics +
			'}';
    }

    @Override public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof CorpusStatistics))
	    return false;
	CorpusStatistics that = (CorpusStatistics) o;
	return Objects.equals(trainingDocumentsCount, that.trainingDocumentsCount) &&
			Objects.equals(testDocumentsCount, that.testDocumentsCount) &&
			Objects.equals(corpusClassesStatistics, that.corpusClassesStatistics);
    }

    @Override public int hashCode() {
	return Objects.hash(trainingDocumentsCount, testDocumentsCount, corpusClassesStatistics);
    }
}

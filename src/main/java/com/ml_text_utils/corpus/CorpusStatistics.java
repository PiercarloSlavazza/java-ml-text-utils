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

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

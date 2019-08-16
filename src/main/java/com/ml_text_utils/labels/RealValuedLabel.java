package com.ml_text_utils.labels;

import java.util.Objects;

public class RealValuedLabel {

    private final double realValue;
    private final String classLabel;

    public RealValuedLabel(double realValue, String classLabel) {
	this.realValue = realValue;
	this.classLabel = classLabel;
    }

    public double getRealValue() {
	return realValue;
    }

    public String getClassLabel() {
	return classLabel;
    }

    @Override public String toString() {
	return "RealValuedLabel{" +
			"realValue=" + realValue +
			", classLabel='" + classLabel + '\'' +
			'}';
    }

    @Override public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof RealValuedLabel))
	    return false;
	RealValuedLabel that = (RealValuedLabel) o;
	return Double.compare(that.realValue, realValue) == 0 &&
			Objects.equals(classLabel, that.classLabel);
    }

    @Override public int hashCode() {
	return Objects.hash(realValue, classLabel);
    }
}

package com.ml_text_utils.features;

import com.ml_text_utils.utils.Vector;

public class LabeledPoint {

    private final double label;
    private final Vector vector;

    public LabeledPoint(double label, Vector vector) {
	this.label = label;
	this.vector = vector;
    }

    public double[] features() {
	return vector.toArray();
    }

    public double label() {
	return label;
    }

}

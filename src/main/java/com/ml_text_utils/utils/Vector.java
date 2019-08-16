package com.ml_text_utils.utils;

import org.apache.commons.math3.linear.RealVector;

public class Vector {

    private RealVector vector;

    Vector(RealVector vector) {
	super();
	this.vector = vector;
    }

    public double[] toArray() {
	return vector.toArray();
    }

}

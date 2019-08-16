package com.ml_text_utils.utils;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.OpenMapRealVector;
import org.apache.commons.math3.linear.SparseRealVector;

public class Vectors {

    public static Vector dense(double[] vector) {
	return new Vector(new ArrayRealVector(vector));
    }

    public static Vector sparse(int size, int[] indexes, double[] features) {

	assert indexes.length == features.length;
	assert size >= indexes.length;

	SparseRealVector vector = new OpenMapRealVector(size);
	for (int i = 0; i < indexes.length; i++) {
	    int index = indexes[i];
	    double feature = features[i];

	    vector.addToEntry(index, feature);
	}


	return new Vector(vector);
    }

}

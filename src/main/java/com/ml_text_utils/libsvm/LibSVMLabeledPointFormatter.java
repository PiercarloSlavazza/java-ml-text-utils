package com.ml_text_utils.libsvm;

import com.ml_text_utils.features.LabeledPoint;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType") public class LibSVMLabeledPointFormatter {

    String formatAsLibSVMRow(LabeledPoint labeledPoint) {
	StringBuilder stringBuilder = new StringBuilder();

	int label = (int)labeledPoint.label();
	stringBuilder.append(new Integer(label)).append(" ");

	double[] features = labeledPoint.features();
	for (int i = 0; i < features.length; i++) {
	    if (features[i] == 0.0f) continue;
	    stringBuilder.append(i + 1).append(":").append(features[i]).append(" ");
	}

	return stringBuilder.toString();
    }

}

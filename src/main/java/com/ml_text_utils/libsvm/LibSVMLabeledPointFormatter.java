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

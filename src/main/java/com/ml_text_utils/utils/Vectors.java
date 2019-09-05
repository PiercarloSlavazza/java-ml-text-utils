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

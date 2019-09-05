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

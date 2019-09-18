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

package com.ml_text_utils.corpus.onehot_encoding_tsv;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OneHotEncoding {

    private final int classesCount;
    private final int zeroBasedClassIndex;

    private final String oneHotEncoding;

    public OneHotEncoding(int classesCount, int zeroBasedClassIndex) {
	this.classesCount = classesCount;
	this.zeroBasedClassIndex = zeroBasedClassIndex;

	this.oneHotEncoding = IntStream.range(0, classesCount).
			mapToObj(scanIndex -> scanIndex == zeroBasedClassIndex ? "1" : "0").
			collect(Collectors.joining());
    }

    @SuppressWarnings("unused") public int getClassesCount() {
	return classesCount;
    }

    @SuppressWarnings("unused") public int getZeroBasedClassIndex() {
	return zeroBasedClassIndex;
    }

    private String getOneHotEncoding() {
	return oneHotEncoding;
    }

    @Override public String toString() {
	return getOneHotEncoding();
    }

    @Override public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof OneHotEncoding))
	    return false;
	OneHotEncoding that = (OneHotEncoding) o;
	return classesCount == that.classesCount &&
			zeroBasedClassIndex == that.zeroBasedClassIndex &&
			Objects.equals(oneHotEncoding, that.oneHotEncoding);
    }

    @Override public int hashCode() {
	return Objects.hash(classesCount, zeroBasedClassIndex, oneHotEncoding);
    }
}

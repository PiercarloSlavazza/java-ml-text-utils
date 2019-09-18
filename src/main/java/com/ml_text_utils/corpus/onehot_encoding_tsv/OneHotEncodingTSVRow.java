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

public class OneHotEncodingTSVRow {

    public enum DataSet {
	TRAIN, TEST, VALIDATION
    }

    private final OneHotEncoding oneHotEncoding;
    private final String text;
    private final DataSet dataSet;

    OneHotEncodingTSVRow(OneHotEncoding oneHotEncoding, String text, DataSet dataSet) {
	this.oneHotEncoding = oneHotEncoding;
	this.text = text;
	this.dataSet = dataSet;
    }

    String getOneHotEncoding() {
	return oneHotEncoding.toString();
    }

    public String getText() {
	return text;
    }

    DataSet getDataSet() {
	return dataSet;
    }

    @Override public String toString() {
	return "OneHotEncodingTSVRow{" +
			"oneHotEncoding=" + oneHotEncoding +
			", text='" + text + '\'' +
			", dataSet=" + dataSet +
			'}';
    }

    @Override public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof OneHotEncodingTSVRow))
	    return false;
	OneHotEncodingTSVRow that = (OneHotEncodingTSVRow) o;
	return Objects.equals(oneHotEncoding, that.oneHotEncoding) &&
			Objects.equals(text, that.text) &&
			dataSet == that.dataSet;
    }

    @Override public int hashCode() {
	return Objects.hash(oneHotEncoding, text, dataSet);
    }
}

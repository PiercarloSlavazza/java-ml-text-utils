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

package com.ml_text_utils.corpus.onehot_encoding_tsv.impl;

import com.ml_text_utils.corpus.onehot_encoding_tsv.OneHotEncoder;
import com.ml_text_utils.corpus.onehot_encoding_tsv.OneHotEncoderFactory;

import java.util.Set;

public class OneHotEncoderFactoryImpl implements OneHotEncoderFactory {

    @Override public OneHotEncoder buildOneHotEncoder(Set<String> classesLabels) {
	return new OneHotEncoderImpl(classesLabels);
    }

}
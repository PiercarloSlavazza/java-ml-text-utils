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

package com.ml_text_utils.nlp;

import com.ml_text_utils.nlp.impl.italian.ItaNLPPreprocessorFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class NLPProcessorFactoryRegistry {

    private static final Map<String, NLPPreprocessorFactory> NLP_PREPROCESSOR_FACTORIES_BY_ISO_6391_LANGUAGE = new HashMap<String, NLPPreprocessorFactory>() {{
       put("it", new ItaNLPPreprocessorFactory());
    }};

    public static NLPPreprocessorFactory getNLPProcessorFactoryByLanguage(String iso6391Language) {
        return Optional.ofNullable(NLP_PREPROCESSOR_FACTORIES_BY_ISO_6391_LANGUAGE.get(iso6391Language)).
			orElseThrow(() -> new RuntimeException(String.format("no NLPProcessorFactory for language|%s|available languages|%s",
									     iso6391Language,
									     String.join(",", NLP_PREPROCESSOR_FACTORIES_BY_ISO_6391_LANGUAGE.keySet())
									     )));
    }
}

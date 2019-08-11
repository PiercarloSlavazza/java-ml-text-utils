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

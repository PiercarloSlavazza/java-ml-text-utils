package com.ml_text_utils.features;

public interface FeaturesExtractor {

    DocumentAsLabeledPoint extractFeaturesFromDocumentInCorpus(String documentId, String classLabel);

}

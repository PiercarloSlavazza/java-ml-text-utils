package com.ml_text_utils.features.tfidf;

import java.util.Set;

public interface CorpusTermsStatistics {

    void addDocument(String documentId, String text);
    void removeDocument(String documentId);
    Set<TfIdf> getTfIdfsForDocumentInCorpus(String documentId);
    Set<TfIdf> getTfIdfsForDocument(String text);

}

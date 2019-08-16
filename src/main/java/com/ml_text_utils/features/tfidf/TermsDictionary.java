package com.ml_text_utils.features.tfidf;

import java.util.List;
import java.util.Optional;

public interface TermsDictionary {

    Optional<Integer> getIndex(String Term);
    Optional<String> getTerm(int index);
    int getSize();
    List<String> getTerms();

}

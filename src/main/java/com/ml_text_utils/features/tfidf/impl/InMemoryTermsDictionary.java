package com.ml_text_utils.features.tfidf.impl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml_text_utils.features.tfidf.TermsDictionary;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryTermsDictionary implements TermsDictionary {

    private Map<String, Integer> dictionary;
    private List<String> sortedTerms;

    @SuppressWarnings("unused") private InMemoryTermsDictionary() {}

    InMemoryTermsDictionary(Map<String, Integer> dictionary) {
	this.dictionary = dictionary;
	this.sortedTerms = dictionary.keySet().stream().sorted().collect(Collectors.toList());
    }

    public static InMemoryTermsDictionary readFromJSONFile(File termsDictionaryJSONFile) {
	ObjectMapper objectMapper = new ObjectMapper();
	objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
	objectMapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
	try {
	    return objectMapper.readValue(termsDictionaryJSONFile, InMemoryTermsDictionary.class);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public Optional<Integer> getIndex(String term) {
	return Optional.ofNullable(dictionary.get(term));
    }

    @Override
    public int getSize() {
	return dictionary.size();
    }

    @Override
    public Optional<String> getTerm(int index) {
	try {
	    return Optional.of(sortedTerms.get(index));
	} catch (IndexOutOfBoundsException e) {
	    return Optional.empty();
	}
    }

    @Override
    public List<String> getTerms() {
	return new ArrayList<>(sortedTerms);
    }
}

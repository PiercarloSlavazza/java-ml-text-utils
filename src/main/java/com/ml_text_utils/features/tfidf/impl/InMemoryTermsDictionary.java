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

package com.ml_text_utils.features.tfidf.impl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml_text_utils.features.tfidf.TermsDictionary;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryTermsDictionary implements TermsDictionary {

    private Map<String, Integer> dictionary = new HashMap<>();
    private List<String> sortedTerms;

    @SuppressWarnings("unused") private InMemoryTermsDictionary() {}

    InMemoryTermsDictionary(Set<String> terms) {
        sortedTerms = terms.stream().sorted().collect(Collectors.toList());
	AtomicInteger termIndex = new AtomicInteger(0);
	sortedTerms.forEach(term -> dictionary.put(term, termIndex.getAndIncrement()));
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

    public void serializeToJSON(File termsDictionaryJSONFile) {
	ObjectMapper objectMapper = new ObjectMapper();
	try {
	    objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
	    objectMapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
	    objectMapper.writerWithDefaultPrettyPrinter().writeValue(termsDictionaryJSONFile, this);
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

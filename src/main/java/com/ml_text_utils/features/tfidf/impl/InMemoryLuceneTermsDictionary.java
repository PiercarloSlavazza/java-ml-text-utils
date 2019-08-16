package com.ml_text_utils.features.tfidf.impl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml_text_utils.features.tfidf.TermsDictionary;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.store.NIOFSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.ml_text_utils.features.tfidf.impl.LuceneFields.DOC_CONTENT_FIELD;
import static com.ml_text_utils.features.tfidf.impl.LuceneFields.DOC_ID_FIELD;

public class InMemoryLuceneTermsDictionary implements TermsDictionary {

    private final static Logger log = LoggerFactory.getLogger(InMemoryLuceneTermsDictionary.class);

    private final InMemoryTermsDictionary inMemoryTermsDictionary;

    public InMemoryLuceneTermsDictionary(File luceneIndexFolder) {
	try (IndexReader indexReader = IndexReader.open(NIOFSDirectory.open(luceneIndexFolder), true)) {
	    inMemoryTermsDictionary = buildDictionary(indexReader);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public void serializeToJSON(File termsDictionaryJSONFile) {
	ObjectMapper objectMapper = new ObjectMapper();
	try {
	    objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
	    objectMapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
	    objectMapper.writerWithDefaultPrettyPrinter().writeValue(termsDictionaryJSONFile, inMemoryTermsDictionary);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    private InMemoryTermsDictionary buildDictionary(IndexReader indexReader) throws IOException {

	log.info("building in memory dictionary|start");

	log.info("collecting terms|start");
	Set<String> terms = new HashSet<>();
	for (int docId = 0; docId < indexReader.maxDoc(); docId++) {
	    if (indexReader.isDeleted(docId)) continue;

	    log.debug("document|" + docId + "|" + indexReader.document(docId).get(DOC_ID_FIELD));
	    TermFreqVector termFreqVector = indexReader.getTermFreqVector(docId, DOC_CONTENT_FIELD);
	    if (termFreqVector == null) continue;

	    terms.addAll(Arrays.asList(termFreqVector.getTerms()));
	}
	log.info("collecting terms|end");

	AtomicInteger index = new AtomicInteger(0);
	Map<String, Integer> dictionary = new HashMap<>();
	terms.forEach(term -> dictionary.put(term, index.getAndIncrement()));

	log.info("building in memory dictionary|end");

	return new InMemoryTermsDictionary(dictionary);
    }

    @Override
    public Optional<Integer> getIndex(String term) {
	return inMemoryTermsDictionary.getIndex(term);
    }

    @Override
    public int getSize() {
	return inMemoryTermsDictionary.getSize();
    }

    @Override
    public Optional<String> getTerm(int index) {
	return inMemoryTermsDictionary.getTerm(index);
    }

    @Override
    public List<String> getTerms() {
	return inMemoryTermsDictionary.getTerms();
    }

}

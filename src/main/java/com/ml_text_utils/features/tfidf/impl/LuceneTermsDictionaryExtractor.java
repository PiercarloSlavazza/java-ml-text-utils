package com.ml_text_utils.features.tfidf.impl;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.store.NIOFSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.ml_text_utils.features.tfidf.impl.LuceneFields.DOC_CONTENT_FIELD;
import static com.ml_text_utils.features.tfidf.impl.LuceneFields.DOC_ID_FIELD;

@SuppressWarnings({ "SuspiciousMethodCalls", "OptionalUsedAsFieldOrParameterType" }) public class LuceneTermsDictionaryExtractor {

    private final static Logger log = LoggerFactory.getLogger(LuceneTermsDictionaryExtractor.class);
    private static final int MIN_TERMS_DOCS_OCCURRENCES = 2;

    private Set<String> pickMostFrequentTerms(Map<String, Integer> termsDocumentsOccurrences, Integer maxTerms) {
	return termsDocumentsOccurrences.
			keySet().
			stream().
			peek(term -> log.info("freq|" + term + "|" + termsDocumentsOccurrences.get(term) + "|keep|" + (termsDocumentsOccurrences.get(term) > MIN_TERMS_DOCS_OCCURRENCES))).
			filter(term -> termsDocumentsOccurrences.get(term) > MIN_TERMS_DOCS_OCCURRENCES).
			sorted(Comparator.comparing(termsDocumentsOccurrences::get).reversed()).
			limit(maxTerms).
			collect(Collectors.toSet());
    }

    private Map<String, Integer> countDocumentOccurrences(Set<String> terms, IndexReader indexReader) {
	Map<String, Integer> termsDocumentsOccurrences = new HashMap<>();
	terms.forEach(term -> {
	    try {
		int numberOfDocumentWithTerm = indexReader.docFreq(new Term(DOC_CONTENT_FIELD, term));
		termsDocumentsOccurrences.put(term, numberOfDocumentWithTerm);
	    } catch (IOException e) {
		throw new RuntimeException(e);
	    }
	});
	return termsDocumentsOccurrences;
    }

    private Set<String> collectAllTermsInIndex(IndexReader indexReader) throws IOException {
	Set<String> allTerms = new HashSet<>();
	for (int docId = 0; docId < indexReader.maxDoc(); docId++) {
	    if (indexReader.isDeleted(docId)) continue;

	    log.debug("document|" + docId + "|" + indexReader.document(docId).get(DOC_ID_FIELD));
	    TermFreqVector termFreqVector = indexReader.getTermFreqVector(docId, DOC_CONTENT_FIELD);
	    if (termFreqVector == null) continue;

	    allTerms.addAll(Arrays.asList(termFreqVector.getTerms()));
	}
	return allTerms;
    }

    private InMemoryTermsDictionary buildDictionary(IndexReader indexReader, Integer maxTerms) throws IOException {

	log.info("building in memory dictionary|start");

	log.info("collecting terms|start");
	Set<String> allTerms = collectAllTermsInIndex(indexReader);
	log.info("collecting terms|end");

	log.info("counting documents for each term|start");
	Map<String, Integer> termsDocumentsOccurrences = countDocumentOccurrences(allTerms, indexReader);
	log.info("counting documents for each term|end");

	Set<String> termsFilteredByDictionaryCap = pickMostFrequentTerms(termsDocumentsOccurrences, maxTerms);

	log.info("building in memory dictionary|end|terms #|" + termsFilteredByDictionaryCap.size());

	return new InMemoryTermsDictionary(termsFilteredByDictionaryCap);
    }

    public InMemoryTermsDictionary buildDictionary(File luceneIndexFolder, Optional<Integer> maxTerms) {
	try (IndexReader indexReader = IndexReader.open(NIOFSDirectory.open(luceneIndexFolder), true)) {
	    return buildDictionary(indexReader, maxTerms.orElse(Integer.MAX_VALUE));
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }
}

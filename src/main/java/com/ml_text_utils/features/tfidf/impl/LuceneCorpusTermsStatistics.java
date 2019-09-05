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

import com.ml_text_utils.features.tfidf.CorpusTermsStatistics;
import com.ml_text_utils.features.tfidf.TfIdf;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.search.DefaultSimilarity;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.NIOFSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ml_text_utils.features.tfidf.impl.LuceneFields.DOC_CONTENT_FIELD;
import static com.ml_text_utils.features.tfidf.impl.LuceneFields.DOC_ID_FIELD;
import static com.ml_text_utils.utils.FileUtils.ensureFileExists;
import static com.ml_text_utils.utils.FileUtils.ensureFileIsFolder;
import static com.ml_text_utils.utils.LuceneUtils.findDocById;
import static com.ml_text_utils.utils.LuceneUtils.getIndexWriter;

class TermFrequency {

    private String term;
    private int frequency;

    TermFrequency(String term, int frequency) {
	super();
	this.term = term;
	this.frequency = frequency;
    }

    String getTerm() {
	return term;
    }

    int getFrequency() {
	return frequency;
    }

}

public class LuceneCorpusTermsStatistics implements CorpusTermsStatistics {

    @SuppressWarnings("unused") private final static Logger log = LoggerFactory.getLogger(LuceneCorpusTermsStatistics.class);

    private final static DefaultSimilarity DEFAULT_SIMILARITY = new DefaultSimilarity();

    private final File luceneIndexFolder;
    private final Analyzer analyzer;

    public LuceneCorpusTermsStatistics(File luceneIndexFolder, Analyzer analyzer) {
	ensureFileExists(luceneIndexFolder);
	ensureFileIsFolder(luceneIndexFolder);

	this.luceneIndexFolder = luceneIndexFolder;
	this.analyzer = analyzer;
    }

    @Override public void addDocument(String documentId, String text) {
	try (IndexWriter indexWriter = getIndexWriter(analyzer, luceneIndexFolder)) {
	    Document luceneDocument = new Document();

	    luceneDocument.add(new Field(DOC_CONTENT_FIELD, new StringReader(text), Field.TermVector.YES));
	    luceneDocument.add(new Field(DOC_ID_FIELD, documentId, Field.Store.YES, Field.Index.NOT_ANALYZED));

	    indexWriter.addDocument(luceneDocument);
	} catch (IOException e) {
	    throw new RuntimeException("cannot index doc|" + documentId, e);
	}
    }

    @Override public void removeDocument(String documentId) {
	throw new NotImplementedException("");
    }

    private TfIdf getTfIdf(TermFrequency termFrequency, IndexReader indexReader) {
	try {
	    int numberOfDocumentWithTerm = indexReader.docFreq(new Term(DOC_CONTENT_FIELD, termFrequency.getTerm()));

	    float tf = DEFAULT_SIMILARITY.tf(termFrequency.getFrequency());
	    float idf = DEFAULT_SIMILARITY.idf(numberOfDocumentWithTerm, indexReader.numDocs());
	    float tfIdf = tf * idf;

	    return new TfIdf(termFrequency.getTerm(), tfIdf);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    private Set<TfIdf> getTfIdfs(Integer luceneDocId, IndexReader indexReader) {
	try {
	    TermFreqVector termsAndFrequencies = indexReader.getTermFreqVector(luceneDocId, DOC_CONTENT_FIELD);
	    if (termsAndFrequencies == null) {
	        log.warn("cannot get terms frequencies for doc|id|" + luceneDocId);
	        return Collections.emptySet();
	    }

	    int frequencies[] = termsAndFrequencies.getTermFrequencies();
	    String[] terms = termsAndFrequencies.getTerms();
	    List<TermFrequency> termsFrequencies = new ArrayList<TermFrequency>() {{
		for (int i = 0; i < terms.length; i++) add(new TermFrequency(terms[i],frequencies[i]));
	    }};

	    return termsFrequencies.stream().map(termFrequency -> getTfIdf(termFrequency, indexReader)).collect(Collectors.toSet());
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override public Set<TfIdf> getTfIdfsForDocumentInCorpus(String documentId) {
	try (IndexReader indexReader = openIndexReader(); IndexSearcher indexSearcher = new IndexSearcher(indexReader)) {
	    return findDocById(documentId, indexSearcher).
			    map(luceneDocId -> getTfIdfs(luceneDocId, indexReader)).
			    orElseThrow(() -> new RuntimeException("cannot find document in corpus index|id|" + documentId));
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    private IndexReader openIndexReader() throws IOException {
	return IndexReader.open(NIOFSDirectory.open(luceneIndexFolder), true);
    }

    @Override public Set<TfIdf> getTfIdfsForDocument(String text) {
	throw new NotImplementedException("");
    }
}

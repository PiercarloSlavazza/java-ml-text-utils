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

package com.ml_text_utils.utils;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.NIOFSDirectory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static com.ml_text_utils.features.tfidf.impl.LuceneFields.DOC_ID_FIELD;

public class LuceneUtils {

    public static Optional<Integer> findDocById(String documentId, Searcher indexSearcher) throws IOException {
	Query documentQuery = new TermQuery(new Term(DOC_ID_FIELD, documentId));
	TopDocs topDocs = indexSearcher. search(documentQuery, 1);
	return Arrays.stream(topDocs.scoreDocs).findFirst().map(scoreDoc -> scoreDoc.doc);
    }

    public static void deleteDoc(int docId, IndexReader indexReader)  {
	try {
	    indexReader.deleteDocument(docId);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public static IndexWriter getIndexWriter(Analyzer analyzer, File luceneIndexFolder) throws IOException {
	return new IndexWriter(new NIOFSDirectory(luceneIndexFolder), analyzer, IndexWriter.MaxFieldLength.UNLIMITED);
    }

}

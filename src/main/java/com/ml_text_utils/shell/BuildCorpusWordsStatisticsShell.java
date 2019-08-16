package com.ml_text_utils.shell;

import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.Option;
import com.ml_text_utils.features.tfidf.CorpusTermsStatistics;
import com.ml_text_utils.features.tfidf.impl.LuceneCorpusTermsStatistics;
import com.ml_text_utils.utils.FileUtils;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import static com.ml_text_utils.utils.FileUtils.*;

interface BuildCorpusWordsStatisticsShellConfig {

    @Option
    File getCorpusFolderRoot();

    @Option
    File getLuceneIndexFolder();

}

public class BuildCorpusWordsStatisticsShell {

    private final static Logger log = LoggerFactory.getLogger(BuildCorpusWordsStatisticsShell.class);

    public static void main(String[] args) {

	BuildCorpusWordsStatisticsShellConfig config = CliFactory.parseArguments(BuildCorpusWordsStatisticsShellConfig.class, args);
	log.info("start|configs" + config.toString());

	ensureFileExists(config.getCorpusFolderRoot());
	ensureFileIsFolder(config.getCorpusFolderRoot());

	CorpusTermsStatistics corpusTermsStatistics = new LuceneCorpusTermsStatistics(config.getLuceneIndexFolder(), new WhitespaceAnalyzer());

	AtomicInteger documentsIndexed = new AtomicInteger(0);
	streamTextFilesInSubFolders(config.getCorpusFolderRoot()).
			peek(file -> log.info(String.format("%d|indexing|%s", documentsIndexed.incrementAndGet(), file.getName()))).
			forEach(file -> corpusTermsStatistics.addDocument(file.getName(), FileUtils.readFile(file)));
    }

}

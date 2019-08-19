package com.ml_text_utils.shell;

import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.Option;
import com.ml_text_utils.features.tfidf.impl.InMemoryTermsDictionary;
import com.ml_text_utils.features.tfidf.impl.LuceneTermsDictionaryExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Optional;

interface ExportTermsDictionaryFromLuceneShellConfig {

    @Option
    File getLuceneIndexFolder();

    @Option
    File getTermsDictionaryOutputJSONFile();

    @Option
    Integer getMaxTerms();
    @SuppressWarnings("unused") boolean isMaxTerms();
}

public class JSONExportTermsDictionaryFromLuceneShell {

    private final static Logger log = LoggerFactory.getLogger(JSONExportTermsDictionaryFromLuceneShell.class);

    public static void main(String[] args) {

	ExportTermsDictionaryFromLuceneShellConfig config = CliFactory.parseArguments(ExportTermsDictionaryFromLuceneShellConfig.class, args);
	log.info("start|configs" + config.toString());

	LuceneTermsDictionaryExtractor luceneTermsDictionaryExtractor = new LuceneTermsDictionaryExtractor();
	InMemoryTermsDictionary inMemoryTermsDictionary = luceneTermsDictionaryExtractor.buildDictionary(config.getLuceneIndexFolder(), Optional.ofNullable(config.getMaxTerms()));
	inMemoryTermsDictionary.serializeToJSON(config.getTermsDictionaryOutputJSONFile());
    }

}

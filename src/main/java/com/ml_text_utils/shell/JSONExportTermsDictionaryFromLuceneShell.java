package com.ml_text_utils.shell;

import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.Option;
import com.ml_text_utils.features.tfidf.impl.InMemoryLuceneTermsDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

interface ExportTermsDictionaryFromLuceneShellConfig {

    @Option
    File getLuceneIndexFolder();

    @Option
    File getTermsDictionaryOutputJSONFile();
}

public class JSONExportTermsDictionaryFromLuceneShell {

    private final static Logger log = LoggerFactory.getLogger(JSONExportTermsDictionaryFromLuceneShell.class);

    public static void main(String[] args) {

	ExportTermsDictionaryFromLuceneShellConfig config = CliFactory.parseArguments(ExportTermsDictionaryFromLuceneShellConfig.class, args);
	log.info("start|configs" + config.toString());

	InMemoryLuceneTermsDictionary termsDictionary = new InMemoryLuceneTermsDictionary(config.getLuceneIndexFolder());
	termsDictionary.serializeToJSON(config.getTermsDictionaryOutputJSONFile());
    }

}

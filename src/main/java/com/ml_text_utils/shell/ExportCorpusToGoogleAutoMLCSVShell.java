package com.ml_text_utils.shell;

import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.Option;
import com.ml_text_utils.corpus.google_automl.GoogleAutoMLCSVExported;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

interface ExportCorpusToGoogleAutoMLCSVShellConfig {

    @Option
    File getCorpusFolderRoot();

    @Option
    File getGoogleAutoMlCsvFile();

    @Option
    String getGoogleCloudStorageFolderUri();
}

public class ExportCorpusToGoogleAutoMLCSVShell {

    private final static Logger log = LoggerFactory.getLogger(ExportCorpusToGoogleAutoMLCSVShell.class);

    public static void main(String[] args) {

	ExportCorpusToGoogleAutoMLCSVShellConfig config = CliFactory.parseArguments(ExportCorpusToGoogleAutoMLCSVShellConfig.class, args);
	log.info("start|configs" + config.toString());

	assert !config.getGoogleCloudStorageFolderUri().endsWith("/");

	new GoogleAutoMLCSVExported(config.getGoogleCloudStorageFolderUri()).exportCorpusToCSV(config.getCorpusFolderRoot(), config.getGoogleAutoMlCsvFile());

    }

}

package com.ml_text_utils.shell;

import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.Option;
import com.ml_text_utils.corpus.NLPCorpusPreprocesser;
import com.ml_text_utils.nlp.NLPPreprocessor;
import com.ml_text_utils.nlp.NLPProcessorFactoryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

interface PreProcessCorpusShellConfig {

    @Option
    File getCorpusFolderRoot();

    @Option
    File getPreprocessedCorpusFolderRoot();

    @Option
    String getIso6391Language();
}

public class PreProcessCorpusShell {

    private final static Logger log = LoggerFactory.getLogger(PreprocessFileShell.class);

    public static void main(String[] args) {

	PreProcessCorpusShellConfig config = CliFactory.parseArguments(PreProcessCorpusShellConfig.class, args);
	log.info("start|configs" + config.toString());

	NLPPreprocessor nlpPreprocessor = NLPProcessorFactoryRegistry.getNLPProcessorFactoryByLanguage(config.getIso6391Language()).buildNLPPreprocessor();
	NLPCorpusPreprocesser nlpCorpusPreprocesser = new NLPCorpusPreprocesser(nlpPreprocessor);

	nlpCorpusPreprocesser.preprocessCorpus(config.getCorpusFolderRoot(), config.getPreprocessedCorpusFolderRoot());
    }

}

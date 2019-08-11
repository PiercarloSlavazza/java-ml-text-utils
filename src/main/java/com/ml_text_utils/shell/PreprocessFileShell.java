package com.ml_text_utils.shell;

import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.Option;
import com.ml_text_utils.nlp.NLPPreprocessor;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static com.ml_text_utils.nlp.NLPProcessorFactoryRegistry.getNLPProcessorFactoryByLanguage;

interface PreprocessFileShellConfig {

    @Option
    File getInputFile();

    @Option
    String getIso6391Language();
}

public class PreprocessFileShell {

    private final static Logger log = LoggerFactory.getLogger(PreprocessFileShell.class);

    public static void main(String[] args) throws IOException {

	PreprocessFileShellConfig config = CliFactory.parseArguments(PreprocessFileShellConfig.class, args);
	log.info("start|configs" + config.toString());

	NLPPreprocessor nlpPreprocessor = getNLPProcessorFactoryByLanguage(config.getIso6391Language()).buildNLPPreprocessor();

	String text = FileUtils.readFileToString(config.getInputFile(), "UTF-8");
	String preprocessedText = nlpPreprocessor.preprocess(text);

	log.info("preprocessed text|\n" + preprocessedText);
    }

}

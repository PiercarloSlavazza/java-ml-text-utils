package com.ml_text_utils.shell;

import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.Option;
import com.ml_text_utils.features.FeaturesExtractor;
import com.ml_text_utils.features.tfidf.CorpusTermsStatistics;
import com.ml_text_utils.features.tfidf.TermsDictionary;
import com.ml_text_utils.features.tfidf.TfIdfFeaturesExtractor;
import com.ml_text_utils.features.tfidf.impl.InMemoryTermsDictionary;
import com.ml_text_utils.features.tfidf.impl.LuceneCorpusTermsStatistics;
import com.ml_text_utils.labels.RealValueLabelsDictionary;
import com.ml_text_utils.labels.impl.CorpusFileSystemRealValueLabelsDictionary;
import com.ml_text_utils.libsvm.LibSVMCorpusFormatter;
import com.ml_text_utils.libsvm.LibSVMLabeledPointFormatter;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Optional;

import static com.ml_text_utils.corpus.FileSystemCorpusBuilder.TEST_FOLDER_NAME;
import static com.ml_text_utils.corpus.FileSystemCorpusBuilder.TRAINING_FOLDER_NAME;

interface FormatCorpusAsLibSVMShellConfig {

    @Option
    File getLuceneIndexFolder();

    @Option
    File getTermsDictionaryJSONFile();

    @Option
    File getCorpusFolderRoot();

    @Option
    File getLibSVMExportFolder();

    @Option
    String getLibSVMExportFilePrefix();
}

public class FormatCorpusAsLibSVMShell {

    private final static Logger log = LoggerFactory.getLogger(FormatCorpusAsLibSVMShell.class);

    public static void main(String[] args) {

	FormatCorpusAsLibSVMShellConfig config = CliFactory.parseArguments(FormatCorpusAsLibSVMShellConfig.class, args);
	log.info("start|configs" + config.toString());

	RealValueLabelsDictionary realValueLabelsDictionary = new CorpusFileSystemRealValueLabelsDictionary(config.getCorpusFolderRoot());
	TermsDictionary termsDictionary = InMemoryTermsDictionary.readFromJSONFile(config.getTermsDictionaryJSONFile());
	CorpusTermsStatistics corpusTermsStatistics = new LuceneCorpusTermsStatistics(config.getLuceneIndexFolder(), new WhitespaceAnalyzer());
	FeaturesExtractor featuresExtractor = new TfIdfFeaturesExtractor(termsDictionary,
									 corpusTermsStatistics,
									 realValueLabelsDictionary);
	LibSVMLabeledPointFormatter libSVMLabeledPointFormatter = new LibSVMLabeledPointFormatter();
	LibSVMCorpusFormatter libSVMCorpusFormatter = new LibSVMCorpusFormatter(featuresExtractor, libSVMLabeledPointFormatter);
	libSVMCorpusFormatter.exportToLibSVMFormat(new File(config.getCorpusFolderRoot() + File.separator + TRAINING_FOLDER_NAME),
						   new File(config.getCorpusFolderRoot() + File.separator + TEST_FOLDER_NAME),
						   config.getLibSVMExportFolder(),
						   config.getLibSVMExportFilePrefix());
    }

}

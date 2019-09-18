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

package com.ml_text_utils.shell;

import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.Option;
import com.ml_text_utils.corpus.onehot_encoding_tsv.OneHotEncodingTSVExporter;
import com.ml_text_utils.corpus.onehot_encoding_tsv.impl.OneHotEncoderFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

interface ExportCorpusToOneHotEncodingTSVShellConfig {

    @Option
    File getCorpusFolderRoot();

    @Option
    File getOneHotEncodingFolder();
}

public class ExportCorpusToOneHotEncodingTSVShell {

    private final static Logger log = LoggerFactory.getLogger(ExportCorpusToOneHotEncodingTSVShell.class);

    private static final String TRAIN_FILE_NAME = "train.tsv";
    private static final String DEV_FILE_NAME = "dev.tsv";
    private static final String TEST_FILE_NAME = "test.tsv";

    public static void main(String[] args) {

	ExportCorpusToOneHotEncodingTSVShellConfig config = CliFactory.parseArguments(ExportCorpusToOneHotEncodingTSVShellConfig.class, args);
	log.info("start|configs" + config.toString());

	File trainFile = new File(config.getOneHotEncodingFolder().getPath() + File.separator + TRAIN_FILE_NAME);
	File testFile = new File(config.getOneHotEncodingFolder().getPath() + File.separator + TEST_FILE_NAME);
	File devFile = new File(config.getOneHotEncodingFolder().getPath() + File.separator + DEV_FILE_NAME);

	new OneHotEncodingTSVExporter(new OneHotEncoderFactoryImpl()).exportCorpusToTSVs(config.getCorpusFolderRoot(),
											 trainFile,
											 devFile,
											 testFile);

    }

}

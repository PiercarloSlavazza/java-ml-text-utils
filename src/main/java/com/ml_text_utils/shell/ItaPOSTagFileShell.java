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
import com.ml_text_utils.nlp.Tokenizer;
import com.ml_text_utils.nlp.impl.italian.ItaOpenNLPTokenizerFactory;
import com.ml_text_utils.nlp.impl.italian.ItaPOSDictionaryFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

interface ItaPOSTagFileShellConfig {

    @Option
    File getInputFile();

}

public class ItaPOSTagFileShell {

    private final static Logger log = LoggerFactory.getLogger(ItaPOSTagFileShell.class);

    public static void main(String[] args) throws IOException {

	ItaPOSTagFileShellConfig config = CliFactory.parseArguments(ItaPOSTagFileShellConfig.class, args);
	log.info("start|configs" + config.toString());

	Tokenizer tokenizer = new ItaOpenNLPTokenizerFactory(new ItaPOSDictionaryFactory()).buildOpenNLPTokenizer();

	String text = FileUtils.readFileToString(config.getInputFile(), "UTF-8");
	String posTaggedText = tokenizer.tokenize(text).
			map(token -> token.getToken() + token.getPos().
					map(pos -> "[" + pos.getId() + pos.getLabel().
							map(label -> ":" + StringUtils.abbreviate(label, 15)).
							orElse("") + "]").
					orElse("")).
			collect(Collectors.joining(" "));

	log.info("posTaggedText text|\n" + posTaggedText);
    }

}

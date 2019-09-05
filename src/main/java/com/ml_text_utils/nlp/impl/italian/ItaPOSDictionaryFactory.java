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

package com.ml_text_utils.nlp.impl.italian;

import com.ml_text_utils.nlp.POSDictionary;
import com.ml_text_utils.nlp.POSDictionaryFactory;
import com.ml_text_utils.utils.Classpath;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class POSLabel {
    private String pos;
    private String label;

    POSLabel(String pos, String label) {
	super();
	this.pos = pos;
	this.label = label;
    }

    String getPos() {
	return pos;
    }

    String getLabel() {
	return label;
    }

    @Override
    public String toString() {
	return "POSLabel [pos=" + pos + ", label=" + label + "]";
    }

}

public class ItaPOSDictionaryFactory implements POSDictionaryFactory {

    private Map<String, String> posDictionary;

    public ItaPOSDictionaryFactory() {
	try (InputStream posDictionaryInputStream = Classpath.getResourceAsStream("it-pos-dictionary.txt")) {

	    List<String> lines = IOUtils.readLines(posDictionaryInputStream, "UTF-8");
	    List<POSLabel> posLabels = lines.stream().filter(StringUtils::isNotEmpty).map(line -> {
		String[] posAndLabel = line.split("\t");
		return new POSLabel(posAndLabel[0], posAndLabel[1]);
	    }).collect(Collectors.toList());
	    posDictionary = new HashMap<>();
	    for (POSLabel posLabel : posLabels) posDictionary.put(posLabel.getPos(), posLabel.getLabel());
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    @Override public POSDictionary buildPOSDictionary() {
	return new ItaPOSDictionary(posDictionary);
    }

}

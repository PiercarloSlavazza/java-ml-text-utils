package com.ml_text_utils.labels.impl;

import com.ml_text_utils.labels.RealValueLabelsDictionary;
import com.ml_text_utils.labels.RealValuedLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static com.ml_text_utils.utils.FileUtils.streamLeafSubFolders;

public class CorpusFileSystemRealValueLabelsDictionary implements RealValueLabelsDictionary {

    private final static Logger log = LoggerFactory.getLogger(CorpusFileSystemRealValueLabelsDictionary.class);

    private final Map<String, RealValuedLabel> realValuedLabelMap = new HashMap<>();

    public CorpusFileSystemRealValueLabelsDictionary(File corpusRootFolder) {
        mapFileSystemCorpusToRealValueLabels(corpusRootFolder);
        log.info("classes read|");
        realValuedLabelMap.keySet().
			stream().
			map(realValuedLabelMap::get).
			sorted(Comparator.comparing(RealValuedLabel::getClassLabel)).
			forEach(realValuedLabel -> log.info(realValuedLabel.toString()));
    }

    private void mapFileSystemCorpusToRealValueLabels(File corpusRootFolder) {
	List<String> sortedLeafFolders = streamLeafSubFolders(corpusRootFolder).
			map(File::getName).
			distinct().
			sorted().
			collect(Collectors.toList());

	for (int i = 0; i < sortedLeafFolders.size(); i++) {
	    String classLabel = sortedLeafFolders.get(i);
	    RealValuedLabel realValuedLabel = new RealValuedLabel(new Integer(i).doubleValue(), classLabel);
	    realValuedLabelMap.put(classLabel, realValuedLabel);
	}
    }

    @Override public RealValuedLabel getRealValueLabelForClass(String classLabel) {
	return Optional.ofNullable(realValuedLabelMap.get(classLabel)).orElseThrow(RuntimeException::new);
    }

}

# Overview

This project implements a number of Java utilities which might be useful in Machine Learning NLP tasks.

Namely it allows for:

* laying out a training/test corpus into the file system
* NLP preprocsssing the documents (tokenization, stemming, POS filtering...)
* extracting features (e.g. TfIdf), and converting them to LibSVM file format

This project is featured in the essay ["What is the best method for Automatic Text Classification?"](https://medium.com/@piercarlo_slavazza/what-is-the-best-method-for-automatic-text-classification).

WARNING: for the time being, this project _must not_ considered production ready due the lack of adequate automatic testing.

# Pipeline example

1. lay out the corpus into the file system
    * have a look to this [example](https://github.com/PiercarloSlavazza/wired-it-text-classification-ml/blob/master/src/main/java/com/ml_text_utils/shell/BuildWiredItFileSystemCorpusShell.java) from another related project
1. NLP preprocess
    * class `com.ml_text_utils.shell.PreProcessCorpusShell`
    * parameters `--corpusFolderRoot <corpus_root> --preprocessedCorpusFolderRoot <corpus_root>_preprocessed --iso6391Language it`
1. TfIdf export to LibSVM
    1. Build Lucene Index
        * class `com.ml_text_utils.shell.BuildCorpusWordsStatisticsShell`
        * parameters `--corpusFolderRoot <corpus_root>_preprocessed --luceneIndexFolder <corpus_root>_preprocessed_lucene`
    1. Export "Terms Dictionary" from Lucene Index
        * class `com.ml_text_utils.shell.JSONExportTermsDictionaryFromLuceneShell`
        * parameters `--luceneIndexFolder <corpus_root>_preprocessed_lucene\ --termsDictionaryOutputJSONFile <corpus_root>_preprocessed_lucene_terms.json --maxTerms 10000`
            * `--maxTerms` is optional
    1. Compute TfIdf and export to LibSVM
        * class `com.ml_text_utils.shell.FormatCorpusAsLibSVMShell`
        * parameters `--corpusFolderRoot <corpus_root>_preprocessed\ --libSVMExportFilePrefix <corpus_name> --libSVMExportFolder <libsvm_files_output_folder> --luceneIndexFolder <corpus_root>_preprocessed_lucene\ --termsDictionaryJSONFile <corpus_root>_preprocessed_lucene_terms.json`
        
## Customize NLP for language other than Italian

Yuo basically just have to implement for your language the classes located in the package `com.ml_text_utils.nlp.impl.italian`.

# Export to Google Cloud AutoML CSV

* class `com.ml_text_utils.shell.ExportCorpusToGoogleAutoMLCSVShell`
* parameters `--corpusFolderRoot <corpus_root> --googleAutoMlCsvFile <corpus>.csv --googleCloudStorageFolderUri gs://<your bucket>/<your folder path if any>`

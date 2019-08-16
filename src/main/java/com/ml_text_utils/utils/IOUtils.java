package com.ml_text_utils.utils;

import java.io.IOException;
import java.io.Writer;

public class IOUtils {

    public static void writeln(String line, Writer writer) {
	try {
	    writer.write(line + "\n");
	} catch (IOException var3) {
	    throw new RuntimeException(var3);
	}
    }

}

package com.ml_text_utils.utils;

import java.io.InputStream;

public class Classpath {

    public static InputStream getResourceAsStream(String file) {
	return Classpath.class.getClassLoader().getResourceAsStream(file);
    }

}

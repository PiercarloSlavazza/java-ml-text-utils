/*
 Copyright (c) 2016 KIE srl.
 All rights reserved.
 http://www.kie-services.com
 mailto:info@kie-services.com
 */
package com.ml_text_utils.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({ "unused", "WeakerAccess" }) public class FileUtils {

    public static List<String> readLines(InputStream inputStream) {
	try (
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

	    List<String> lines = new ArrayList<>();
	    String line;
	    while ((line = bufferedReader.readLine()) != null) lines.add(line);
	    return lines;
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public static void ensureFileIsFolder(File folder) {
	if (folder.isDirectory()) return;
	throw new RuntimeException("File is not a folder|file|" + folder);
    }

    public static void ensureFileExists(File file) {
	if (file.exists()) return;
	throw new RuntimeException("File does not exists|file|" + file);
    }

    public static File createTempFile(String prefix, String suffix) {
	try {
	    File tempFile = File.createTempFile(prefix, suffix);
	    tempFile.deleteOnExit();

	    return  tempFile;
	} catch (IOException e) {
	    throw new RuntimeException("cannot write temporary PDF file", e);
	}
    }

    public static File writeToTempFile(String text, String prefix, String suffix) {
	try (InputStream textInputStream = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8))) {
	    return writeToTempFile(textInputStream, prefix, suffix);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public static File writeToTempFile(InputStream inputStream, String prefix, String suffix) {
	File tempFile = createTempFile(prefix, suffix);
	try {
	    Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
	    return  tempFile;
	} catch (IOException e) {
	    throw new RuntimeException("cannot write input stream to file|" + tempFile, e);
	}
    }

    public static String replaceExtension(String fileName, String newExtension) {
	int lastIndexOfDot = fileName.lastIndexOf(".");
	if (lastIndexOfDot < 0) return fileName;
	String fileNameWithoutExtension = fileName.substring(0, lastIndexOfDot);
	return fileNameWithoutExtension + "." + newExtension;
    }
}

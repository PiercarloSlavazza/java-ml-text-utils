package com.ml_text_utils.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings({ "unused", "WeakerAccess" }) public class FileUtils {

    private static final FilenameFilter TEXT_FILENAME_FILTER = (dir, name) -> name.toLowerCase().endsWith(".txt");
    public static final String UTF_8 = "UTF-8";

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

    private static boolean isLeafFolder(File folder) {
        return streamSubFolders(folder).count() == 0;
    }

    public static Stream<File> streamLeafSubFolders(File folder) {
	return streamSubFolders(folder).
			map(FileUtils::streamSubFolders).
			reduce(Stream::concat).
			orElse(Stream.empty()).
			filter(FileUtils::isLeafFolder);
    }

    public static Stream<File> streamSubFolders(File folder) {
	return Arrays.stream(folder.listFiles(File::isDirectory));
    }

    public static Stream<File> streamTextFiles(File folder) {
	return Arrays.stream(folder.listFiles(TEXT_FILENAME_FILTER));
    }

    public static Stream<File> streamTextFilesInSubFolders(File folder) {
	Stream<File> fileStreamInFolder = streamTextFiles(folder);
	Stream<File> fileStreamInSubFolders = streamSubFolders(folder).
			map(FileUtils::streamTextFilesInSubFolders).
			reduce(Stream::concat).
			orElse(Stream.empty());
	return Stream.concat(fileStreamInFolder, fileStreamInSubFolders);
    }

    public static String readFile(File file) {
	try {
	    return org.apache.commons.io.FileUtils.readFileToString(file, UTF_8);
	} catch (IOException e) {
	    throw new RuntimeException("cannot read file|" + file, e);
	}
    }
}

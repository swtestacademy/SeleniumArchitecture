package com.saha.slnarch.common.file;

import com.google.common.base.Preconditions;
import com.saha.slnarch.common.file.parser.JsonParser;
import com.saha.slnarch.common.file.parser.Parser;
import com.saha.slnarch.common.file.parser.YamlParser;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileHelper implements FileParser, FileReader, FileWriter {

  private static final Charset UTF8 = StandardCharsets.UTF_8;
  private static final String DOT = ".";
  private static final String YAML = DOT + "yaml";
  private static final String JSON = DOT + "json";
  private static final String XML = DOT + "xml";
  private static final String PROP = DOT + "properties";

  public FileHelper() {

  }

  @Override
  public <T> T parseFile(String filePath, Class<T> output) {
    Preconditions.checkNotNull(filePath);
    T t = null;
    try {
      t = getParser(filePath).parseFile(getFileStream(filePath),output);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return t;
  }

  private Parser getParser(String filePath) throws Exception {
    Parser parser = null;
    if (filePath.endsWith(YAML)) {
      parser = new YamlParser();
    } else if (filePath.endsWith(JSON)) {
      parser = new JsonParser();
    }
//    } else if (filePath.endsWith(XML)) {
//      parser = new JsonParser<>();
//    } else if (filePath.endsWith(PROP)) {
//      parser = new JsonParser<>();
//    }
    else {
      throw new Exception("File Type Not Found");
    }
    return parser;
  }


  @Override
  public String readFileAsString(String filePath) {
    InputStream inputStream = null;
    try {
      inputStream = getFileStream(filePath);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return toString(inputStream);
  }

  @Override
  public String readFileAsString(File file) {
    return null;
  }

  @Override
  public String readFileAsString(URI uri) {
    return null;
  }

  private InputStream getFileStream(String filePath) throws Exception {
    return getFileStream(toFile(filePath));
  }


  private InputStream getFileStream(File file) throws Exception {
    try {
      return new FileInputStream(file);
    } catch (Exception e) {
      throw new Exception(e);
    }
  }



  private File toFile(String path) {
    return new File(path);
  }

  private URI toUrI(String path) {
    return toFile(path).getAbsoluteFile().toURI();
  }

  private URI toUrI(File file) {
    return file.getAbsoluteFile().toURI();
  }



  private ByteArrayOutputStream toByteStream(InputStream is) {
    ByteArrayOutputStream result = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int length;
    try {
      while ((length = is.read(buffer)) != -1) {
        result.write(buffer, 0, length);
      }
      return result;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  private String toString(InputStream is) {
    try {
      return toByteStream(is).toString(UTF8.name());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private String toString(byte[] bytes) {
    if (bytes == null) {
      return null;
    }
    return new String(bytes, UTF8);
  }

  private byte[] toBytes(InputStream is) {
    return toByteStream(is).toByteArray();
  }


  @Override
  public boolean writeToFile(String path, String data) {
    return false;
  }

  @Override
  public boolean writeToFile(File file, String data) {
    return false;
  }
}
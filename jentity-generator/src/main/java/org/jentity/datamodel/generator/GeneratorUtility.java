package org.jentity.datamodel.generator;

import java.util.*;

public class GeneratorUtility {

  public static String firstToUpperCase(String string) {
    String post = string.substring(1,string.length());
    String first = (""+string.charAt(0)).toUpperCase();
    return first+post;
  }

  public static String firstToLowerCase(String string) {
    String post = string.substring(1,string.length());
    String first = (""+string.charAt(0)).toLowerCase();
    return first+post;
  }

  // Type conversion

  private static Hashtable<String,String> typeConvertor = new Hashtable<String,String>();
  static {
    typeConvertor.put("integer","int");
    typeConvertor.put("boolean","boolean");
    typeConvertor.put("date","java.util.Date");
    typeConvertor.put("long","long");
    typeConvertor.put("short","short");
    typeConvertor.put("double","double");
    typeConvertor.put("string","String");
  }

  public static String getJavaType(String type) {
    String javaType = (String) typeConvertor.get(type);
    if (javaType==null)
      return type;
    return javaType;
  }
}
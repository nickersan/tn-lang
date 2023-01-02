package com.tn.lang;

public class Strings
{
  public static final String EMPTY = "";

  public static boolean isNullOrWhitespace(String s)
  {
    return s == null || s.trim().length() == 0;
  }

  public static String orElseEmpty(String s, boolean b)
  {
    return b ? s : EMPTY;
  }
}

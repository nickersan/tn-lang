package com.tn.lang;

public class Strings
{
  public static final String EMPTY = "";

  public static String orElseEmpty(String s, boolean b)
  {
    return b ? s : EMPTY;
  }
}

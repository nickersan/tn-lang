package com.tn.lang;

public class Strings
{
  public static final String EMPTY = "";

  public static boolean isNullOrWhitespace(String s)
  {
    return s == null || s.trim().isEmpty();
  }

  public static boolean isNotNullOrWhitespace(String s)
  {
    return !isNullOrWhitespace(s);
  }

  public static String orElseEmpty(String s, boolean b)
  {
    return b ? s : EMPTY;
  }

  public static String repeat(String s, String join, int times)
  {
    StringBuilder stringBuilder = new StringBuilder();

    for (int i = 0; i < times; i++)
    {
      stringBuilder.append(s);
      if (i < times - 1) stringBuilder.append(join);
    }

    return stringBuilder.toString();
  }
}

package com.tn.lang;

public class Objects
{
  public static <T> T coalesce(T a, T b)
  {
    return a != null ? a : b;
  }
}

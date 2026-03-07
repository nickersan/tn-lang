package com.tn.lang.util.stream;

import java.util.function.BinaryOperator;

public class MergeFunctions
{
  public static <T> BinaryOperator<T> first()
  {
    //noinspection unused
    return (first, last) -> first;
  }

  public static <T> BinaryOperator<T> last()
  {
    //noinspection unused
    return (first, last) -> last;
  }
}

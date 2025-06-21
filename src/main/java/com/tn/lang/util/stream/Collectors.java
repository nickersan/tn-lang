package com.tn.lang.util.stream;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;

public class Collectors
{
  public static <T, R> Collector<T, ?, Map<R, T>> by(Function<T, R> getter)
  {
    return java.util.stream.Collectors.toMap(getter, Function.identity());
  }
}

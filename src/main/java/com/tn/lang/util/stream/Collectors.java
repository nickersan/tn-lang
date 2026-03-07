package com.tn.lang.util.stream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;

public class Collectors
{
  public static <T, R> Collector<T, ?, Map<R, T>> by(Function<T, R> getter)
  {
    return java.util.stream.Collectors.toMap(getter, Function.identity());
  }

  public static <T, R> Collector<T, ?, Map<R, T>> by(Function<T, R> getter, BinaryOperator<T> mergeFunction)
  {
    return java.util.stream.Collectors.toMap(getter, Function.identity(), mergeFunction);
  }

  public static <T> Collector<T, ?, List<T>> toSortedList(Comparator<? super T> comparator)
  {
    return java.util.stream.Collectors.collectingAndThen(
      java.util.stream.Collectors.toCollection(ArrayList::new),
      list ->
      {
        list.sort(comparator);
        return list;
      }
    );
  }

  public static <T> Collector<T, ?, SortedSet<T>> toSortedSet(Comparator<? super T> comparator)
  {
    return java.util.stream.Collectors.toCollection(() -> new TreeSet<>(comparator));
  }
}

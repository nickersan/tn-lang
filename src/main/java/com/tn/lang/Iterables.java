package com.tn.lang;

import static java.util.stream.StreamSupport.stream;

import java.util.Collection;
import java.util.List;

public class Iterables
{
  public static boolean isEmpty(Iterable<?> iterable)
  {
    return iterable instanceof Collection ? ((Collection<?>)iterable).isEmpty() : iterable == null || !iterable.iterator().hasNext();
  }

  public static <T> List<T> toList(Iterable<T> iterable)
  {
    return iterable instanceof List ? (List<T>)iterable : stream(iterable.spliterator(), false).toList();
  }
}

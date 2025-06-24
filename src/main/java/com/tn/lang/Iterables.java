package com.tn.lang;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.StreamSupport.stream;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public class Iterables
{
  public static boolean isEmpty(Iterable<?> iterable)
  {
    return iterable instanceof Collection ? ((Collection<?>)iterable).isEmpty() : iterable == null || !iterable.iterator().hasNext();
  }

  public static boolean isNotEmpty(Iterable<?> iterable)
  {
    return !isEmpty(iterable);
  }

  public static int size(Iterable<?> iterable)
  {
    if (iterable instanceof Collection) return ((Collection<?>)iterable).size();

    int exactSize = (int)iterable.spliterator().getExactSizeIfKnown();
    if (exactSize >= 0) return exactSize;

    Iterator<?> iterator = iterable.iterator();
    int size = 0;
    while (iterator.hasNext())
    {
      iterator.next();
      size++;
    }

    return size;
  }

  public static <T> T[] asArray(Iterable<T> iterable, IntFunction<T[]> generator)
  {
    return asStream(iterable).toArray(generator);
  }

  public static <T> List<T> asList(Iterable<T> iterable)
  {
    return iterable instanceof List ? (List<T>)iterable : asStream(iterable).toList();
  }

  public static <T> Set<T> asSet(Iterable<T> iterable)
  {
    return iterable instanceof Set ? (Set<T>)iterable : asStream(iterable).collect(toSet());
  }

  public static <T> Stream<T> asStream(Iterable<T> iterable)
  {
    return stream(iterable.spliterator(), false);
  }
}

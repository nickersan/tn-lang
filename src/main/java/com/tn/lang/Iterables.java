package com.tn.lang;

import static java.util.stream.StreamSupport.stream;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Iterables
{
  public static boolean isEmpty(Iterable<?> iterable)
  {
    return iterable instanceof Collection ? ((Collection<?>)iterable).isEmpty() : iterable == null || !iterable.iterator().hasNext();
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

  public static <T> List<T> toList(Iterable<T> iterable)
  {
    return iterable instanceof List ? (List<T>)iterable : stream(iterable.spliterator(), false).toList();
  }
}

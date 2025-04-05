package com.tn.lang.util;

import static java.util.Collections.unmodifiableCollection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import javax.annotation.Nonnull;

public class Page<T> implements Iterable<T>
{
  private final Collection<T> items;
  private final int number;
  private final int count;
  private final int size;

  public Page(@Nonnull Collection<T> items, int number, int count, int size)
  {
    this.items = unmodifiableCollection(items);
    this.number = number;
    this.count = count;
    this.size = size;
  }

  public Collection<T> items()
  {
    return items;
  }

  public int number()
  {
    return number;
  }

  public int count()
  {
    return count;
  }

  public int size()
  {
    return size;
  }

  @Override
  public @Nonnull Iterator<T> iterator()
  {
    return items.iterator();
  }

  @Override
  public boolean equals(Object other)
  {
    if (other == null || getClass() != other.getClass()) return false;

    return number == ((Page<?>)other).number &&
      count == ((Page<?>)other).count &&
      size == ((Page<?>)other).size &&
      Objects.equals(items, ((Page<?>)other).items);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(number, count, size);
  }

  @Override
  public String toString()
  {
    return "Page{number=" + number +
      ", count=" + count +
      ", size=" + size +
      ", items=" + items +
    "}";
  }
}

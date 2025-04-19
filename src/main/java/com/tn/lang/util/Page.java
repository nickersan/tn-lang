package com.tn.lang.util;

import static java.util.Collections.unmodifiableCollection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import javax.annotation.Nonnull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Page<T> implements Iterable<T>
{
  @JsonProperty
  private final Collection<T> items;
  @JsonProperty
  private final int number;
  @JsonProperty
  private final int count;
  @JsonProperty
  private final int totalItems;

  @JsonCreator
  public Page(
    @Nonnull
    @JsonProperty("items")
    Collection<T> items,
    @JsonProperty("number")
    int number,
    @JsonProperty("count")
    int count,
    @JsonProperty("totalItems")
    int totalItems
  )
  {
    this.items = unmodifiableCollection(items);
    this.number = number;
    this.count = count;
    this.totalItems = totalItems;
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

  public int totalItems()
  {
    return totalItems;
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

    //noinspection SuspiciousMethodCalls
    return number == ((Page<?>)other).number &&
      count == ((Page<?>)other).count &&
      totalItems == ((Page<?>)other).totalItems &&
      items.size() == ((Page<?>)other).items.size() &&
      items.containsAll(((Page<?>)other).items);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(number, count, totalItems);
  }

  @Override
  public String toString()
  {
    return "Page{number=" + number +
      ", count=" + count +
      ", totalItems=" + totalItems +
      ", items=" + items +
    "}";
  }
}

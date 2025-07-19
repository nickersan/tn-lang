package com.tn.lang.util;

import static java.util.Collections.unmodifiableCollection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

import jakarta.annotation.Nonnull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Page<T> implements Iterable<T>
{
  @JsonProperty
  private final Collection<T> items;
  @JsonProperty
  private final int number;
  @JsonProperty
  private final int size;
  @JsonProperty
  private final long totalItems;
  @JsonProperty
  private final int totalPages;

  @JsonCreator
  public Page(
    @Nonnull
    @JsonProperty("items")
    Collection<T> items,
    @JsonProperty("number")
    int number,
    @JsonProperty("size")
    int size,
    @JsonProperty("totalItems")
    long totalItems,
    @JsonProperty("totalPages")
    int totalPages
  )
  {
    this.items = unmodifiableCollection(items);
    this.number = number;
    this.size = size;
    this.totalItems = totalItems;
    this.totalPages = totalPages;
  }

  public Collection<T> items()
  {
    return items;
  }

  public int number()
  {
    return number;
  }

  public int size()
  {
    return size;
  }

  public long totalItems()
  {
    return totalItems;
  }

  public long totalPages()
  {
    return totalPages;
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
      size == ((Page<?>)other).size &&
      totalItems == ((Page<?>)other).totalItems &&
      totalPages == ((Page<?>)other).totalPages &&
      items.size() == ((Page<?>)other).items.size() &&
      items.containsAll(((Page<?>)other).items);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(number, size, totalItems, totalPages);
  }

  @Override
  public String toString()
  {
    return "Page{number=" + number +
      ", size=" + size +
      ", totalItems=" + totalItems +
      ", totalPages=" + totalPages +
      ", items=" + items +
    "}";
  }
}

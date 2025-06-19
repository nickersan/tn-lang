package com.tn.lang;

import static java.util.Collections.emptyList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jakarta.annotation.Nonnull;

import org.junit.jupiter.api.Test;

class IterablesTest
{
  @Test
  void shouldReturnEmptyTrue()
  {
    assertTrue(Iterables.isEmpty(new ArrayIterable<>()));
  }

  @Test
  void shouldReturnEmptyTrueWithCollection()
  {
    assertTrue(Iterables.isEmpty(emptyList()));
  }

  @Test
  void shouldReturnEmptyFalse()
  {
    assertFalse(Iterables.isEmpty(new ArrayIterable<>(1)));
  }

  @Test
  void shouldReturnEmptyFalseWithCollection()
  {
    assertFalse(Iterables.isEmpty(List.of(1)));
  }

  @Test
  void shouldReturnNotEmptyTrue()
  {
    assertTrue(Iterables.isNotEmpty(new ArrayIterable<>(1)));
  }

  @Test
  void shouldReturnNotEmptyTrueWithCollection()
  {
    assertTrue(Iterables.isNotEmpty(List.of(1)));
  }

  @Test
  void shouldReturnNotEmptyFalse()
  {
    assertFalse(Iterables.isNotEmpty(new ArrayIterable<>()));
  }

  @Test
  void shouldReturnNotEmptyFalseWithCollection()
  {
    assertFalse(Iterables.isNotEmpty(emptyList()));
  }

  @Test
  void shouldReturnSize()
  {
    assertEquals(3, Iterables.size(new ArrayIterable<>(1, 2, 3)));
  }

  @Test
  void shouldReturnSizeWithList()
  {
    List<Integer> list = List.of(1, 2, 3);
    assertEquals(list.size(), Iterables.size(list));
  }

  @Test
  void shouldReturnArray()
  {
    assertArrayEquals(new Integer[]{ 1, 2, 3 }, Iterables.asArray(new ArrayIterable<>(1, 2, 3), Integer[]::new));
  }

  @Test
  void shouldReturnList()
  {
    assertEquals(List.of(1, 2, 3), Iterables.asList(new ArrayIterable<>(1, 2, 3)));
  }

  @Test
  void shouldReturnListWithList()
  {
    List<Integer> list = List.of(1, 2, 3);
    assertSame(list, Iterables.asList(list));
  }

  @Test
  void shouldReturnSet()
  {
    assertEquals(Set.of(1, 2, 3), Iterables.asSet(new ArrayIterable<>(1, 2, 3)));
  }

  @Test
  void shouldReturnSetWithSet()
  {
    Set<Integer> set = Set.of(1, 2, 3);
    assertSame(set, Iterables.asSet(set));
  }

  private static class ArrayIterable<T> implements Iterable<T>
  {
    private final T[] array;

    private int index = 0;

    @SafeVarargs
    public ArrayIterable(T... array)
    {
      this.array = array;
    }

    @Override
    public @Nonnull Iterator<T> iterator()
    {
      return new Iterator<>()
      {
        @Override
        public boolean hasNext()
        {
          return index < array.length;
        }

        @Override
        public T next()
        {
          return array[index++];
        }
      };
    }
  }
}

package com.tn.lang.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class PageTest
{
  @Test
  void shouldInitialize()
  {
    List<Integer> items = List.of(1, 2, 3);
    int number = 1;
    int count = 10;
    int size = 5;
    Page<Integer> page = new Page<>(items, number, count, size);

    assertArrayEquals(items.toArray(Integer[]::new), page.items().toArray(Integer[]::new));
    assertEquals(number, page.number());
    assertEquals(count, page.count());
    assertEquals(size, page.size());
    assertIterableEquals(items, page);
  }
}

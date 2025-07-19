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
    int number = 0;
    int size = 3;
    int totalItems = 5;
    int totalPages = 2;

    Page<Integer> page = new Page<>(items, number, size, totalItems, totalPages);

    assertArrayEquals(items.toArray(Integer[]::new), page.items().toArray(Integer[]::new));
    assertEquals(number, page.number());
    assertEquals(size, page.size());
    assertEquals(totalItems, page.totalItems());
    assertEquals(totalPages, page.totalPages());
    assertIterableEquals(items, page);
  }

  @Test
  void shouldBeEqual()
  {
    List<Integer> items = List.of(1, 2, 3);
    int number = 0;
    int size = 3;
    int totalItems = 5;
    int totalPages = 2;

    Page<Integer> page1 = new Page<>(items, number, size, totalItems, totalPages);
    Page<Integer> page2 = new Page<>(items, number, size, totalItems, totalPages);

    assertEquals(page1, page2);
    assertEquals(page1.hashCode(), page2.hashCode());
  }
}

package com.tn.lang.util.stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MergeFunctionsTest
{
  @Test
  void shouldReturnFirst()
  {
    Object first = new Object();
    Object last = new Object();

    assertEquals(first, MergeFunctions.first().apply(first, last));
  }

  @Test
  void shouldReturnLast()
  {
    Object first = new Object();
    Object last = new Object();

    assertEquals(last, MergeFunctions.last().apply(first, last));
  }
}

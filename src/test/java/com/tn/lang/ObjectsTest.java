package com.tn.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ObjectsTest
{
  @Test
  void shouldCoalesce()
  {
    Object a = new Object();
    Object b = new Object();

    assertEquals(a, Objects.coalesce(a, b));
    assertEquals(b, Objects.coalesce(null, b));
    //noinspection ConstantValue
    assertNull(Objects.coalesce(null, null));
  }
}

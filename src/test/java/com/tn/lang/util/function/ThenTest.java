package com.tn.lang.util.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

class ThenTest
{
  @Test
  void shouldCallBefore()
  {
    AtomicBoolean flag = new AtomicBoolean(false);
    Then.beforeThen(() -> flag.set(true)).then(() -> assertTrue(flag.get()));
  }

  @Test
  void shouldCallBeforeThenReturn()
  {
    AtomicBoolean flag = new AtomicBoolean(false);
    assertTrue(Then.beforeThen(() -> flag.set(true)).thenReturn(flag::get));
  }

  @Test
  void shouldCallAfter()
  {
    AtomicBoolean flag = new AtomicBoolean(false);
    Then.thenAfter(() -> flag.set(true)).then(() -> assertFalse(flag.get()));
    assertTrue(flag.get());
  }

  @Test
  void shouldReturnThenCallAfter()
  {
    AtomicBoolean flag = new AtomicBoolean(false);
    assertFalse(Then.thenAfter(() -> flag.set(true)).thenReturn(flag::get));
    assertTrue(flag.get());
  }

  @Test
  void shouldCallBeforeAndAfter()
  {
    AtomicInteger count = new AtomicInteger(0);
    Then.beforeThenAfter(count::incrementAndGet, count::incrementAndGet).then(() -> assertEquals(1, count.get()));
    assertEquals(2, count.get());
  }

  @Test
  void shouldCallBeforeAndAfterThenReturn()
  {
    AtomicInteger count = new AtomicInteger(0);
    assertEquals(1, Then.beforeThenAfter(count::incrementAndGet, count::incrementAndGet).thenReturn(count::get));
    assertEquals(2, count.get());
  }
}

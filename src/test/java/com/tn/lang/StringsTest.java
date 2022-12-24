package com.tn.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringsTest
{
  @Test
  void shouldReturnStringWhenPredicateTrue()
  {
    String s = "test";

    assertEquals(s, Strings.orElseEmpty(s, true));
  }

  @Test
  void shouldReturnEmptyWhenPredicateFalse()
  {
    Assertions.assertEquals(Strings.EMPTY, Strings.orElseEmpty("test", false));
  }
}

package com.tn.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static com.tn.lang.Strings.EMPTY;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringsTest
{
  @Test
  void shouldTrueWhenNull()
  {
    assertTrue(Strings.isNullOrWhitespace(null));
  }

  @Test
  void shouldTrueWhenEmpty()
  {
    assertTrue(Strings.isNullOrWhitespace(EMPTY));
  }

  @Test
  void shouldTrueWhenWhitespace()
  {
    assertTrue(Strings.isNullOrWhitespace(" "));
  }

  @Test
  void shouldFalseWhenNull()
  {
    assertFalse(Strings.isNotNullOrWhitespace(null));
  }

  @Test
  void shouldFalseWhenEmpty()
  {
    assertFalse(Strings.isNotNullOrWhitespace(EMPTY));
  }

  @Test
  void shouldFalseWhenWhitespace()
  {
    assertFalse(Strings.isNotNullOrWhitespace(" "));
  }

  @Test
  void shouldFalseWhenContainsCharacters()
  {
    assertFalse(Strings.isNullOrWhitespace("A"));
    assertFalse(Strings.isNullOrWhitespace(" B"));
    assertFalse(Strings.isNullOrWhitespace("C "));
    assertFalse(Strings.isNullOrWhitespace(" D "));
  }

  @Test
  void shouldReturnStringWhenPredicateTrue()
  {
    String s = "test";

    assertEquals(s, Strings.orElseEmpty(s, true));
  }

  @Test
  void shouldReturnEmptyWhenPredicateFalse()
  {
    Assertions.assertEquals(EMPTY, Strings.orElseEmpty("test", false));
  }

  @Test
  void shouldRepeat()
  {
    Assertions.assertEquals("AxAxA", Strings.repeat("A", "x", 3));
  }
}

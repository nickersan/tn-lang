package com.tn.lang.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LocalDateTimesTest
{
  @ParameterizedTest
  @MethodSource("nanos")
  void shouldRound(int nanos, int roundedNanos)
  {
    LocalDateTime localDateTime = LocalDateTime.now();
    assertEquals(roundedNanos, LocalDateTimes.roundMicros(localDateTime.withNano(nanos)).getNano());
  }

  private static Stream<Arguments> nanos()
  {
    return Stream.of(
      Arguments.arguments(0, 0),
      Arguments.arguments(123456000, 123456000),
      Arguments.arguments(123456001, 123456000),
      Arguments.arguments(123456499, 123456000),
      Arguments.arguments(123456500, 123457000),
      Arguments.arguments(123456999, 123457000)
    );
  }
}

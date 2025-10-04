package com.tn.lang.time;

import static java.time.temporal.ChronoUnit.MICROS;

import java.time.LocalDateTime;

public class LocalDateTimes
{
  public static LocalDateTime roundMicros(LocalDateTime localDateTime)
  {
    return localDateTime.withNano((int)(Math.round(localDateTime.getNano() / 1000.0) * 1000)).truncatedTo(MICROS);
  }
}

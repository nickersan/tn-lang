package com.tn.lang.time;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class LocalClockTest
{
  @Test
  void shouldReturnCurrentDate()
  {
    assertNotNull(new LocalClock().currentDate());
  }

  @Test
  void shouldReturnCurrentTime()
  {
    assertNotNull(new LocalClock().currentTime());
  }
}

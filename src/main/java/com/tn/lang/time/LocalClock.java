package com.tn.lang.time;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LocalClock implements Clock
{
  @Override
  public LocalDate currentDate()
  {
    return LocalDate.now();
  }

  @Override
  public LocalDateTime currentTime()
  {
    return LocalDateTime.now();
  }
}

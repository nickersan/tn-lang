package com.tn.lang.time;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface Clock
{
  LocalDate currentDate();

  LocalDateTime currentTime();
}

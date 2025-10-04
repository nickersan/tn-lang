package com.tn.lang.util.function;

import java.util.function.Supplier;

public class Then
{
  private final Runnable before;
  private final Runnable after;

  private Then(Runnable before, Runnable after)
  {
    this.before = before;
    this.after = after;
  }

  public static Then beforeThen(Runnable before)
  {
    return new Then(before, () -> {});
  }

  public static Then thenAfter(Runnable after)
  {
    return new Then(() -> {}, after);
  }

  public static Then beforeThenAfter(Runnable before, Runnable after)
  {
    return new Then(before, after);
  }

  public void then(Runnable runnable)
  {
    before.run();

    try
    {
      runnable.run();
    }
    finally
    {
      after.run();
    }
  }

  public <T> T thenReturn(Supplier<T> supplier)
  {
    before.run();

    try
    {
      return supplier.get();
    }
    finally
    {
      after.run();
    }
  }
}

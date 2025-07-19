package com.tn.lang.util.function;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Lambdas
{
  public static Throwable unwrapException(Throwable e)
  {
    return e instanceof WrappedException ? e.getCause() : e;
  }

  public static <T, U> BiConsumer<T, U> wrapBiConsumer(BiConsumerWithThrows<T, U, ?> biConsumerWithThrows)
  {
    return (t, u) ->
    {
      try
      {
        biConsumerWithThrows.accept(t, u);
      }
      catch (Throwable e)
      {
        if (e instanceof RuntimeException) throw (RuntimeException)e;
        else throw new WrappedException(e);
      }
    };
  }

  public static <T> Consumer<T> wrapConsumer(ConsumerWithThrows<T, ?> consumerWithThrows)
  {
    return t ->
    {
      try
      {
        consumerWithThrows.accept(t);
      }
      catch (Throwable e)
      {
        if (e instanceof RuntimeException) throw (RuntimeException)e;
        else throw new WrappedException(e);
      }
    };
  }

  public static <T, R> Function<T, R> wrapFunction(FunctionWithThrows<T, R, ?> functionWithThrows)
  {
    return t ->
    {
      try
      {
        return functionWithThrows.apply(t);
      }
      catch (Throwable e)
      {
        if (e instanceof RuntimeException) throw (RuntimeException)e;
        else throw new WrappedException(e);
      }
    };
  }

  public static <T> Supplier<T> wrapSupplier(SupplierWithThrows<T, ?> supplierWithThrows)
  {
    return () ->
    {
      try
      {
        return supplierWithThrows.get();
      }
      catch (Throwable e)
      {
        if (e instanceof RuntimeException) throw (RuntimeException)e;
        else throw new WrappedException(e);
      }
    };
  }
}

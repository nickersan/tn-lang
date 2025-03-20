package com.tn.lang.util.function;

@FunctionalInterface
public interface ConsumerWithThrows<T, E extends Throwable>
{
  static <T, E extends Throwable> ConsumerWithThrows<T, E> noop()
  {
    return (T t) -> {};
  }

  void accept(T t) throws E;
}

package com.tn.lang.util.function;

@FunctionalInterface
public interface FunctionWithThrows<T, R, E extends Throwable>
{
  R apply(T t) throws E;
}

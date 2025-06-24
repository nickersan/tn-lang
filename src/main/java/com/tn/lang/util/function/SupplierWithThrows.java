package com.tn.lang.util.function;

@FunctionalInterface
public interface SupplierWithThrows<T, E extends Throwable>
{
  T get() throws E;
}

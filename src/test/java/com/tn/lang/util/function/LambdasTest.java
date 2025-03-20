package com.tn.lang.util.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import static com.tn.lang.util.function.Lambdas.wrapConsumer;
import static com.tn.lang.util.function.Lambdas.wrapSupplier;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

class LambdasTest
{
  @Test
  void testWrapConsumer()
  {
    String expected = "testing";
    AtomicReference<String> stringRef = new AtomicReference<>();
    ConsumerWithThrows<String, Exception> consumerWithThrows = stringRef::set;

    wrapConsumer(consumerWithThrows).accept(expected);

    assertEquals(expected, stringRef.get());
  }

  @Test
  void testWrapConsumerWithException()
  {
    Exception expected = new Exception("testing");
    ConsumerWithThrows<String, Exception> consumerWithThrows = t -> { throw expected; };

    try
    {
      wrapConsumer(consumerWithThrows).accept("abc");
      fail("Failed to rethrow");
    }
    catch (WrappedException e)
    {
      assertEquals(expected, e.getCause());
    }
  }

  @Test
  void testWrapFunction()
  {
    String expected = "testing";
    FunctionWithThrows<String, String, Exception> functionWithThrows = s -> s;

    assertEquals(expected, Lambdas.wrapFunction(functionWithThrows).apply(expected));
  }

  @Test
  void testWrapFunctionWithException()
  {
    Exception expected = new Exception("testing");
    FunctionWithThrows<String, String, Exception> functionWithThrows = s -> { throw expected; };

    try
    {
      Lambdas.wrapFunction(functionWithThrows).apply("abc");
      fail("Failed to rethrow");
    }
    catch (WrappedException e)
    {
      assertEquals(expected, e.getCause());
    }
  }

  @Test
  void testWrapSupplier()
  {
    String expected = "testing";
    SupplierWithThrows<String, Exception> supplierWithThrows = () -> expected;

    assertEquals(expected, wrapSupplier(supplierWithThrows).get());
  }

  @Test
  void testWrapSupplierWithException()
  {
    Exception expected = new Exception("testing");
    SupplierWithThrows<String, Exception> supplierWithThrows = () -> { throw expected; };

    try
    {
      wrapSupplier(supplierWithThrows).get();
      fail("Failed to rethrow");
    }
    catch (WrappedException e)
    {
      assertEquals(expected, e.getCause());
    }
  }
}

package com.tn.lang.sql;

import static com.tn.lang.util.function.Lambdas.unwrapException;
import static com.tn.lang.util.function.Lambdas.wrapConsumer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.function.IntSupplier;

import com.tn.lang.util.function.WrappedException;

public class PreparedStatements
{
  /** How each type of value is set. A type with no entry is set as an object, as is any type that is not a subclass of one that has. */
  private static final Map<Class<?>, Binder<Object>> BINDERS = Map.ofEntries(
    binder(Boolean.class, (preparedStatement, index, value) -> preparedStatement.setBoolean(index, value)),
    binder(Byte.class, (preparedStatement, index, value) -> preparedStatement.setByte(index, value)),
    binder(Character.class, (preparedStatement, index, value) -> preparedStatement.setString(index, value.toString())),
    binder(Date.class, (preparedStatement, index, value) -> preparedStatement.setTimestamp(index, toTimestamp(value))),
    binder(Double.class, (preparedStatement, index, value) -> preparedStatement.setDouble(index, value)),
    binder(Float.class, (preparedStatement, index, value) -> preparedStatement.setFloat(index, value)),
    binder(Integer.class, (preparedStatement, index, value) -> preparedStatement.setInt(index, value)),
    binder(LocalDate.class, (preparedStatement, index, value) -> preparedStatement.setDate(index, toDate(value))),
    binder(LocalDateTime.class, (preparedStatement, index, value) -> preparedStatement.setTimestamp(index, toTimestamp(value))),
    binder(Long.class, (preparedStatement, index, value) -> preparedStatement.setLong(index, value)),
    binder(Short.class, (preparedStatement, index, value) -> preparedStatement.setShort(index, value)),
    binder(String.class, (preparedStatement, index, value) -> preparedStatement.setString(index, value))
  );

  private PreparedStatements() {}

  /**
   * Sets a value at the next index. A collection sets each of its values, at an index each; a null sets nothing and takes no index.
   */
  public static void setValue(PreparedStatement preparedStatement, IntSupplier index, Object value) throws SQLException
  {
    if (value == null) return;

    try
    {
      bind(preparedStatement, index, value);
    }
    catch (WrappedException e)
    {
      rethrowUnwrapped(e);
    }
  }

  private static void bind(PreparedStatement preparedStatement, IntSupplier index, Object value) throws SQLException
  {
    Binder<Object> binder = binderFor(value.getClass());

    if (binder != null) binder.bind(preparedStatement, index.getAsInt(), value);
    else if (value instanceof Collection<?> values) values.forEach(wrapConsumer(v -> setValue(preparedStatement, index, v)));
    else preparedStatement.setObject(index.getAsInt(), value);
  }

  /** Throws what a value of a collection failed with, as it was thrown: the wrapping was only to get it out of the collection's forEach. */
  private static void rethrowUnwrapped(WrappedException e) throws SQLException
  {
    Throwable unwrappedException = unwrapException(e);

    if (unwrappedException instanceof SQLException sqlException) throw sqlException;
    if (unwrappedException instanceof RuntimeException runtimeException) throw runtimeException;

    throw new RuntimeException(unwrappedException);
  }

  /** The way to set a value of the type, or of the nearest superclass that has one: a Timestamp is a Date. */
  private static Binder<Object> binderFor(Class<?> type)
  {
    for (Class<?> candidate = type; candidate != null; candidate = candidate.getSuperclass())
    {
      Binder<Object> binder = BINDERS.get(candidate);

      if (binder != null) return binder;
    }

    return null;
  }

  private static <T> Map.Entry<Class<?>, Binder<Object>> binder(Class<T> type, Binder<T> binder)
  {
    return Map.entry(type, (preparedStatement, index, value) -> binder.bind(preparedStatement, index, type.cast(value)));
  }

  private static java.sql.Date toDate(LocalDate value)
  {
    return java.sql.Date.valueOf(value);
  }

  private static Timestamp toTimestamp(Date value)
  {
    return new Timestamp(value.getTime());
  }

  private static Timestamp toTimestamp(LocalDateTime value)
  {
    return Timestamp.valueOf(value);
  }

  @FunctionalInterface
  private interface Binder<T>
  {
    void bind(PreparedStatement preparedStatement, int index, T value) throws SQLException;
  }
}

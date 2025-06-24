package com.tn.lang.sql;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.tn.lang.util.function.BiConsumerWithThrows;

class PreparedStatementsTest
{
  @ParameterizedTest
  @MethodSource("values")
  void shouldSetValue(Object value, BiConsumerWithThrows<PreparedStatement, Integer, SQLException> preparedStatementSetter) throws SQLException
  {
    int index = 1;

    PreparedStatement preparedStatement = mock(PreparedStatement.class);
    PreparedStatements.setValue(preparedStatement, () -> index, value);

    preparedStatementSetter.accept(verify(preparedStatement), index);
  }

  static Stream<Arguments> values()
  {
    Date date = new Date();
    LocalDate localDate = LocalDate.now();
    LocalDateTime localDateTime = LocalDateTime.now();
    Object object = new Object();

    return Stream.of(
      Arguments.of(Boolean.TRUE, preparedStatementSetter(PreparedStatement::setBoolean, true)),
      Arguments.of((byte)2, preparedStatementSetter(PreparedStatement::setByte, (byte)2)),
      Arguments.of('x', preparedStatementSetter(PreparedStatement::setString, "x")),
      Arguments.of(date, preparedStatementSetter(PreparedStatement::setTimestamp, new Timestamp(date.getTime()))),
      Arguments.of(1.23, preparedStatementSetter(PreparedStatement::setDouble, 1.23)),
      Arguments.of(1.23F, preparedStatementSetter(PreparedStatement::setFloat, 1.23F)),
      Arguments.of(localDate, preparedStatementSetter(PreparedStatement::setDate, java.sql.Date.valueOf(localDate))),
      Arguments.of(localDateTime, preparedStatementSetter(PreparedStatement::setTimestamp, Timestamp.valueOf(localDateTime))),
      Arguments.of(3L, preparedStatementSetter(PreparedStatement::setLong, 3L)),
      Arguments.of((short)3, preparedStatementSetter(PreparedStatement::setShort, (short)3)),
      Arguments.of(object, preparedStatementSetter(PreparedStatement::setObject, object))




//  else if (value instanceof Collection) ((Collection<?>)value).forEach(wrapConsumer(v -> setValue(preparedStatement, index, v)));


    );
  }

  @Test
  void shouldSetValues() throws SQLException
  {
    AtomicInteger index = new AtomicInteger(1);

    int value1 = 1;
    String value2 = "Test";
    boolean value3 = true;

    Collection<?> values = List.of(value1, value2, value3);

    PreparedStatement preparedStatement = mock(PreparedStatement.class);
    PreparedStatements.setValue(preparedStatement, index::getAndIncrement, values);

    verify(preparedStatement).setInt(1, value1);
    verify(preparedStatement).setString(2, value2);
    verify(preparedStatement).setBoolean(3, value3);
  }

  private static <T> BiConsumerWithThrows<PreparedStatement, Integer, SQLException> preparedStatementSetter(PreparedStatementSet<T> preparedStatementSet, T value)
  {
    return (preparedStatement, index) -> preparedStatementSet.set(preparedStatement, index, value);
  }

  @FunctionalInterface
  private interface PreparedStatementSet<T>
  {
    void set(PreparedStatement preparedStatement, int index, T value) throws SQLException;
  }
}

package com.tn.lang.sql;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

/** What {@link PreparedStatements#setValue} does that {@link PreparedStatementsTest} does not say: the other types, kinds of a type, and failures. */
class PreparedStatementsBindingTest
{
  private final PreparedStatement preparedStatement = mock(PreparedStatement.class);

  @Test
  void shouldSetAnIntegerAndAString() throws SQLException
  {
    PreparedStatements.setValue(preparedStatement, () -> 4, 42);
    PreparedStatements.setValue(preparedStatement, () -> 5, "text");

    verify(preparedStatement).setInt(4, 42);
    verify(preparedStatement).setString(5, "text");
  }

  @Test
  void shouldSetNothingForANullAndNotTakeAnIndex() throws SQLException
  {
    AtomicInteger taken = new AtomicInteger();

    PreparedStatements.setValue(preparedStatement, taken::incrementAndGet, null);

    verifyNoInteractions(preparedStatement);
    assertEquals(0, taken.get());
  }

  @Test
  void shouldSetASubclassOfADateAsATimestamp() throws SQLException
  {
    Timestamp timestamp = new Timestamp(1_700_000_000_000L);

    PreparedStatements.setValue(preparedStatement, () -> 1, timestamp);
    PreparedStatements.setValue(preparedStatement, () -> 2, new java.sql.Date(1_700_000_000_000L));

    verify(preparedStatement).setTimestamp(1, timestamp);
    verify(preparedStatement).setTimestamp(2, new Timestamp(1_700_000_000_000L));
  }

  @Test
  void shouldTakeOneIndexForEachValueOfAnyKindOfCollection() throws SQLException
  {
    AtomicInteger index = new AtomicInteger(1);

    PreparedStatements.setValue(preparedStatement, index::getAndIncrement, new LinkedHashSet<>(List.of("a", "b")));
    PreparedStatements.setValue(preparedStatement, index::getAndIncrement, new ArrayList<>(List.of(3L)));

    verify(preparedStatement).setString(1, "a");
    verify(preparedStatement).setString(2, "b");
    verify(preparedStatement).setLong(3, 3L);
    assertEquals(4, index.get());
  }

  @Test
  void shouldSetTheValuesOfACollectionInsideACollection() throws SQLException
  {
    AtomicInteger index = new AtomicInteger(1);

    PreparedStatements.setValue(preparedStatement, index::getAndIncrement, List.of(List.of(1, 2), Set.of(true)));

    verify(preparedStatement).setInt(1, 1);
    verify(preparedStatement).setInt(2, 2);
    verify(preparedStatement).setBoolean(3, true);
  }

  @Test
  void shouldSkipANullInACollectionWithoutTakingAnIndex() throws SQLException
  {
    AtomicInteger index = new AtomicInteger(1);

    PreparedStatements.setValue(preparedStatement, index::getAndIncrement, java.util.Arrays.asList("a", null, "b"));

    verify(preparedStatement).setString(1, "a");
    verify(preparedStatement).setString(2, "b");
  }

  @Test
  void shouldPassOnTheSqlExceptionOfASingleValue() throws SQLException
  {
    SQLException failure = new SQLException("no");
    doThrow(failure).when(preparedStatement).setString(anyInt(), org.mockito.ArgumentMatchers.anyString());

    assertSame(failure, assertThrows(SQLException.class, () -> PreparedStatements.setValue(preparedStatement, () -> 1, "x")));
  }

  @Test
  void shouldPassOnTheSqlExceptionOfAValueInACollectionAsItIs() throws SQLException
  {
    SQLException failure = new SQLException("no");
    doThrow(failure).when(preparedStatement).setString(anyInt(), org.mockito.ArgumentMatchers.anyString());

    assertSame(failure, assertThrows(SQLException.class, () -> PreparedStatements.setValue(preparedStatement, () -> 1, List.of("x"))));
  }

  @Test
  void shouldPassOnARuntimeExceptionOfAValueInACollectionAsItIs() throws SQLException
  {
    IllegalStateException failure = new IllegalStateException("no");
    doThrow(failure).when(preparedStatement).setInt(anyInt(), anyInt());

    assertSame(failure, assertThrows(IllegalStateException.class, () -> PreparedStatements.setValue(preparedStatement, () -> 1, List.of(1))));
  }
}

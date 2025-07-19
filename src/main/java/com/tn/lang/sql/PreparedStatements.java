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
import java.util.function.IntSupplier;

import com.tn.lang.util.function.WrappedException;

public class PreparedStatements
{
  private PreparedStatements() {}

  public static void setValue(PreparedStatement preparedStatement, IntSupplier index, Object value) throws SQLException
  {
    try
    {
      if (value instanceof Boolean) preparedStatement.setBoolean(index.getAsInt(), (Boolean)value);
      else if (value instanceof Byte) preparedStatement.setByte(index.getAsInt(), (Byte)value);
      else if (value instanceof Character) preparedStatement.setString(index.getAsInt(), ((Character)value).toString());
      else if (value instanceof Date) preparedStatement.setTimestamp(index.getAsInt(), toTimestamp((Date)value));
      else if (value instanceof Double) preparedStatement.setDouble(index.getAsInt(), (Double)value);
      else if (value instanceof Float) preparedStatement.setFloat(index.getAsInt(), (Float)value);
      else if (value instanceof Integer) preparedStatement.setInt(index.getAsInt(), (Integer)value);
      else if (value instanceof LocalDate) preparedStatement.setDate(index.getAsInt(), toDate((LocalDate)value));
      else if (value instanceof LocalDateTime) preparedStatement.setTimestamp(index.getAsInt(), toTimestamp((LocalDateTime)value));
      else if (value instanceof Long) preparedStatement.setLong(index.getAsInt(), (Long)value);
      else if (value instanceof Short) preparedStatement.setShort(index.getAsInt(), (Short)value);
      else if (value instanceof String) preparedStatement.setString(index.getAsInt(), (String)value);
      else if (value instanceof Collection) ((Collection<?>)value).forEach(wrapConsumer(v -> setValue(preparedStatement, index, v)));
      else if (value != null) preparedStatement.setObject(index.getAsInt(), value);
    }
    catch (WrappedException e)
    {
      Throwable unwrappedException = unwrapException(e);
      if (unwrappedException instanceof SQLException) throw (SQLException)unwrappedException;
      else if (unwrappedException instanceof RuntimeException) throw (RuntimeException)unwrappedException;
      else throw new RuntimeException(unwrappedException);
    }
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
}

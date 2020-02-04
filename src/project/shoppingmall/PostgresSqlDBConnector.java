package project.shoppingmall;

import java.sql.*;
import java.util.function.Consumer;

public class PostgresSqlDBConnector implements DBConnector {

  private String dbURL = "jdbc:postgresql://arjuna.db.elephantsql.com/sblwxgwu";
  private String user = "sblwxgwu";
  private String password = "mlEWfCE0sZWwkTSxCSbf40LXsFOrIH3n";

  private PostgresSqlDBConnector(){}
  private static PostgresSqlDBConnector connector = new PostgresSqlDBConnector();
  public static PostgresSqlDBConnector getInstance() {
    return connector;
  }

  @Override
  public int insert(String query, Consumer<PreparedStatement> consumer) {
    try (Connection connection = DriverManager.getConnection(dbURL, user, password);
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      connection.setAutoCommit(false);
      consumer.accept(preparedStatement);
      int[] retValue = preparedStatement.executeBatch();
      connection.commit();
      return retValue.length;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return -1;
  }

  @Override
  public ResultSet select(String query, Consumer<PreparedStatement> consumer) {
    try (Connection connection = DriverManager.getConnection(dbURL, user, password);
         PreparedStatement preparedStatement = connection.prepareStatement(query);
    ) {
      consumer.accept(preparedStatement);
      return preparedStatement.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public int update(String query, Consumer<PreparedStatement> consumer) {
    try (Connection connection = DriverManager.getConnection(dbURL, user, password);
         PreparedStatement preparedStatement = connection.prepareStatement(query);) {
      connection.setAutoCommit(false);
      consumer.accept(preparedStatement);
      int retValue = preparedStatement.executeUpdate();
      connection.commit();
      return retValue;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return -1;
  }

  @Override
  public int delete(String query, Consumer<PreparedStatement> consumer) {
    try (Connection connection = DriverManager.getConnection(dbURL, user, password);
         PreparedStatement preparedStatement = connection.prepareStatement(query);) {
      connection.setAutoCommit(false);
      consumer.accept(preparedStatement);
      int retValue = preparedStatement.executeUpdate();
      connection.commit();
      return retValue;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return -1;
  }

}
package project.shopping_mall.db;

import java.sql.*;

public class PostgresSqlDBConnector implements DBConnector {

  static {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  String dbURL = "jdbc:postgressql://arjuna.db.elephantsql.com/sblwxgwu";
  String user = "sblwxgwu";
  String password = "mlEWfCE0sZWwkTSxCSbf40LXsFOrIH3n";
  private static PostgresSqlDBConnector connector = new PostgresSqlDBConnector();

  public static PostgresSqlDBConnector getInstance() {
    return connector;
  }

  @Override
  public int insert(String query) {
    try (Connection connection = DriverManager.getConnection(dbURL, user, password);
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      connection.setAutoCommit(false);
      int[] retValue = preparedStatement.executeBatch();
      connection.commit();
      return retValue.length;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return -1;
  }

  @Override
  public ResultSet select(String query) {
    try (Connection connection = DriverManager.getConnection(dbURL, user, password);
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         ResultSet resultSet = preparedStatement.executeQuery();) {
      return resultSet;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public int update(String query) {
    try (Connection connection = DriverManager.getConnection(dbURL, user, password);
         PreparedStatement preparedStatement = connection.prepareStatement(query);) {
      connection.setAutoCommit(false);
      int retValue = preparedStatement.executeUpdate();
      connection.commit();
      return retValue;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return -1;
  }

  @Override
  public int delete(String query) {
    try (Connection connection = DriverManager.getConnection(dbURL, user, password);
         PreparedStatement preparedStatement = connection.prepareStatement(query);) {
      connection.setAutoCommit(false);
      int retValue = preparedStatement.executeUpdate();
      connection.commit();
      return retValue;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return -1;
  }

}
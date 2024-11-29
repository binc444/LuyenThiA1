package ntu.hieutm.appluyenthia1.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
  static public Connection getConnnection(String dbName, String userMySQL, String passMysSQL){
    String strConn = "jdbc:mysql://localhost:3306/"+dbName;
    Connection conn;
    try {
      conn = DriverManager.getConnection(strConn, userMySQL, passMysSQL);
      return conn;
    } catch (SQLException e){
      e.printStackTrace();
    }
    return null;
  }
}

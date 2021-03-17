/*
 * package com.seller.quickbuy.QuickBuyApp.utils;
 * 
 * import java.sql.Connection; import java.sql.DriverManager; import
 * java.sql.SQLException;
 * 
 * public class ConnectionUtil {
 * 
 * private static Connection conn;
 * 
 * public static Connection getMyConnection() { if (conn == null) { try { String
 * drive = QuickBuyUtils.getPropertyValue("database.url"); String username =
 * QuickBuyUtils.getPropertyValue("database.user"); String password =
 * QuickBuyUtils.getPropertyValue("database.url"); System.out.println(drive);
 * Class.forName("oracle.jdbc.driver.OracleDriver"); conn =
 * DriverManager.getConnection(QuickBuyUtils.getPropertyValue("database.url"),
 * QuickBuyUtils.getPropertyValue("database.user"),
 * QuickBuyUtils.getPropertyValue("database.password"));
 * System.out.println(conn); } catch (ClassNotFoundException | SQLException e) {
 * // TODO Auto-generated catch block e.printStackTrace(); } return conn; }
 * return conn; }
 * 
 * public static void closeMyConnection() { if (conn != null) { try {
 * conn.close(); } catch (SQLException e) { // TODO Auto-generated catch block
 * e.printStackTrace(); } } } }
 */
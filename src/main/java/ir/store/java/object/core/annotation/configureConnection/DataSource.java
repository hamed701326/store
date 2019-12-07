package ir.store.java.object.core.annotation.configureConnection;

import ir.store.java.object.core.annotation.Configuration;

import java.sql.*;

@Configuration
public class DataSource {
    Connection connection = null;
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/store?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    static final String USER = "root";
    static final String Pass = "14tir1375";

    public Connection createConnection() {

        try {
            if (connection != null) {
                System.out.println("can't creat a new connection");
            } else {
                Class.forName(JDBC_DRIVER);
                connection = DriverManager.getConnection(DB_URL, USER, Pass);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

}

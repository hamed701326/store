package ir.store.java.object;
import java.sql.*;

public class MainApp {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/department?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    static final String USER = "root";
    static final String Pass = "14tir1375";

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, Pass);
            statement = connection.createStatement();
            String sqlQuery = "select id,age,firstName,lastName from employee";
            ResultSet rs = statement.executeQuery(sqlQuery);
            System.out.println("id\tage\tfirstName\tlastName");
            while (rs.next()) {
                int id = rs.getInt("id");
                int age = rs.getInt("age");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                System.out.println(id + "\t" + age + "\t" + firstName + "\t" + lastName);
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}

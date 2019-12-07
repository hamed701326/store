package ir.store.java.object.usermanagementfeature.impl;

import ir.store.java.object.core.annotation.Implementation;
import ir.store.java.object.core.annotation.configureConnection.DataSource;
import ir.store.java.object.core.share.AuthenticationService;
import ir.store.java.object.model.user.User;
import ir.store.java.object.usermanagementfeature.usecase.UserDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Implementation
public class UserDAOImpl implements UserDAO {
    public User login(String username, String password) {
        // get connection
        DataSource dataSource = new DataSource();
        try {
            Connection connection = dataSource.createConnection();
            String sql = "select user_id, username, password, role from user where " +
                    " username = ? and password = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUserName(resultSet.getString("username"));
                user.setPassWord(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));
                AuthenticationService.getInstance().setLoginUser(user);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // return result
        return null;
    }

    public void signUp(User user) {
        DataSource dataSource = new DataSource();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String sql = "insert into user " +
                "values (null,?,?,?,now(),?,?,?,?,?)";
        try {
            connection = dataSource.createConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassWord());
            preparedStatement.setString(3, user.getSpecification().getEmailAddress());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.setString(5, user.getAddress().toString());
            preparedStatement.setString(6, user.getSpecification().getName());
            preparedStatement.setString(7, user.getSpecification().getLastName());
            preparedStatement.setString(8, user.getSpecification().getPhoneNumber());
            int row = preparedStatement.executeUpdate();
            if (row == 1) System.out.println("you're sign up successfully");
            else System.out.println("error occurred, sign up later.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean check(String variable, String value) {
        DataSource dataSource = new DataSource();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String sql = "select * from user where " + variable + "='" + value + '\'';
        try {
            connection = dataSource.createConnection();
            preparedStatement = connection.prepareStatement(sql);
            boolean result = preparedStatement.execute();
            if (result) return true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}

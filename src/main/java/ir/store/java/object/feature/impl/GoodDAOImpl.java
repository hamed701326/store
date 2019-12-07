package ir.store.java.object.feature.impl;

import ir.store.java.object.core.annotation.Implementation;
import ir.store.java.object.core.annotation.configureConnection.DataSource;
import ir.store.java.object.feature.usecase.GoodDAO;
import ir.store.java.object.model.Good;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Implementation
public class GoodDAOImpl implements GoodDAO {
    @Override
    public List<Good> getAllGood() {
        DataSource dataSource = new DataSource();
        Connection con = dataSource.createConnection();
        Statement statement = null;
        ResultSet rs = null;
        List<Good> goods = new ArrayList<>();
        try {
            String query = "select * from goods";
            statement = con.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                Good good = new Good();
                good.setId(rs.getInt("id"));
                good.setName(rs.getString("name"));
                good.setStock(rs.getInt("stock"));
                good.setPrice(rs.getDouble("price"));
                good.setDetails(rs.getString("details"));
                goods.add(good);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            finish(con, statement, rs);

        }
        return goods;
    }

    @Override
    public void addGood(Good good) {
        Connection dbConnection = null;
        Statement statement = null;
        if (!existCheck(good.getId())) {
            String sql = "insert into goods values(" + good.getId() + ",'" + good.getName() + "\'," + good.getPrice() + ","
                    + good.getStock() + ",'"
                    + good.getDetails() +
                    "\')";
            try {
                DataSource dataSource = new DataSource();
                dbConnection = dataSource.createConnection();
                statement = dbConnection.prepareStatement(sql);
                statement.executeUpdate(sql);
                System.out.println("Record is inserted into Good table for Good: " + good.getName());
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                finish(dbConnection, statement);
            }
        } else {
            updateGood(good);
        }

    }

    @Override
    public Good getGood(int goodId) {
        DataSource dataSource = new DataSource();
        Connection connection = dataSource.createConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            String query = "select * from goods where  id=" + goodId;
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                Good good = new Good();
                good.setId(rs.getInt("id"));
                good.setName(rs.getString("name"));
                good.setPrice(rs.getDouble("price"));
                good.setStock(rs.getInt("stock"));
                good.setDetails(rs.getString("details"));
                return good;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            finish(connection, stmt, rs);
        }
        return null;
    }


    @Override
    public void updateGood(Good good) {
        DataSource dataSource = new DataSource();
        Connection connection = null;
        PreparedStatement statement = null;
        if (existCheck(good.getId())) {
            String query = "update goods set name=? , stock=? ,  price=? where id =" + good.getId();
            try {
                connection = dataSource.createConnection();
                statement = connection.prepareStatement(query);
                statement.setString(1, good.getName());
                statement.setInt(2, good.getStock());
                statement.setDouble(3, good.getPrice());
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                finish(connection, statement);
            }
        } else {
            System.out.println("There aren't any good with id= " + good.getId());
            System.out.print("Would you like to add this good? \n\t1.Yes \n\t2.No \n respone:");
            if (new Scanner(System.in).nextInt() == 1) {
                addGood(good);
            }
        }
    }

    public boolean existCheck(int id) {
        boolean exist = (getGood(id) != null);
        return exist;
    }

    public void finish(Connection dbConnection, Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void finish(Connection connection, Statement stmt, ResultSet rs) {
        try {
            if (connection != null) {
                connection.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteGood(int goodId) {
        Connection dbConnection = null;
        Statement statement = null;
        String sql = "delete from goods where id=" + goodId;
        try {
            DataSource dataSource = new DataSource();
            dbConnection = dataSource.createConnection();
            statement = dbConnection.prepareStatement(sql);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            finish(dbConnection, statement);
        }

    }

}

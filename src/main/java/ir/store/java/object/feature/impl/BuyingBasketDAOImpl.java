package ir.store.java.object.feature.impl;

import ir.store.java.object.core.annotation.Implementation;
import ir.store.java.object.core.annotation.configureConnection.DataSource;
import ir.store.java.object.feature.usecase.BuyingBasketDAO;
import ir.store.java.object.feature.usecase.GoodDAO;
import ir.store.java.object.model.Good;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Implementation
public class BuyingBasketDAOImpl implements BuyingBasketDAO {

    @Override
    public List<Good> getAllGoods(int userId) {
        DataSource dataSource = new DataSource();
        Connection con = dataSource.createConnection();
        Statement statement = null;
        ResultSet rs = null;
        List<Good> goods = new ArrayList<>();
        try {
            String query = "select * from basket where fk_user=" + userId;
            statement = con.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                Good good = new Good();
                good.setId(rs.getInt("Id_good"));
                good.setName(rs.getString("name"));
                good.setStock(rs.getInt("stock"));
                good.setPrice(rs.getDouble("price"));
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
    public void addGood(Good good, int userId) {
        Connection dbConnection = null;
        PreparedStatement stmt = null;
        String sql = "insert into basket(fk_user, id_good, name, stock, price) " +
                "values(?,?,?,?,?)";
        if (getGood(good.getId(), userId) == null) {
            try {
                DataSource dataSource = new DataSource();
                dbConnection = dataSource.createConnection();
                stmt = dbConnection.prepareStatement(sql);
                stmt.setInt(1, userId);
                stmt.setInt(2, good.getId());
                stmt.setString(3, good.getName());
                stmt.setInt(4, good.getStock());
                stmt.setDouble(5, good.getPrice());
                if (stmt.execute())
                    System.out.println("Record is inserted into buy_basket table for Good: " + good.getName());
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                finish(dbConnection, stmt);
            }
        } else updateGood(good, userId);
    }

    @Override
    public Good getGood(int goodId, int userId) {
        DataSource dataSource = new DataSource();
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            String query = "select * from basket where  id_good=" + goodId + " and fk_user=" + userId;
            connection = dataSource.createConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                Good good = new Good();
                good.setId(rs.getInt("Id"));
                good.setName(rs.getString("name"));
                good.setPrice(rs.getDouble("price"));
                good.setStock(rs.getInt("stock"));
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
    public void updateGood(Good good, int userId) {
        DataSource dataSource = new DataSource();
        Connection dbConnection = null;
        Statement statement = null;
        String sql = "update basket set stock= " +
                good.getStock() + " where id_good =" + good.getId() + " and fk_user=" + userId;
        if (existCheck(good.getId(), userId)) {
            try {
                dbConnection = dataSource.createConnection();
                statement = dbConnection.prepareStatement(sql);
                statement.executeUpdate(sql);
                System.out.println("Record is updated into good table for id :"
                        + good.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                finish(dbConnection, statement);
            }
        } else {
            addGood(good, userId);
        }
    }

    public boolean existCheck(int id, int userId) {
        boolean exist = (getGood(id, userId) != null);
        return exist;
    }

    @Override
    public void deleteGood(int goodId, int userId) {
        DataSource dataSource = new DataSource();
        Connection dbConnection = null;
        Statement statement = null;
        String sql = "delete from basket where id_good=" + goodId;
        if (goodId == 0) sql = "delete from basket";

        try {
            dbConnection = dataSource.createConnection();
            statement = dbConnection.prepareStatement(sql);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            finish(dbConnection, statement);
        }
    }

    private void finish(Connection connection, Statement stmt, ResultSet rs) {
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

    private void finish(Connection dbConnection, Statement statement) {
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

}

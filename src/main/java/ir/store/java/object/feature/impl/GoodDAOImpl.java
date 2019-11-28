package ir.store.java.object.feature.impl;

import ir.store.java.object.core.annotation.configureConnection.DataSource;
import ir.store.java.object.feature.usecase.GoodDAO;
import ir.store.java.object.model.Good;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GoodDAOImpl implements GoodDAO {
    @Override
    public List<Good> getAllGoods() {
        DataSource dataSource=new DataSource();
        Connection con=dataSource.createConnection();
        Statement statement=null;
        ResultSet rs=null;
        List<Good> goods=new ArrayList<>();
        try
        {
            String query="select * from employee";
            statement=con.createStatement();
            rs=statement.executeQuery(query);
            while(rs.next()){
                Good good=new Good();
                good.setId(rs.getInt("Id"));
                good.setName(rs.getString("name"));
                good.setStock(rs.getInt("stock"));
                good.setPrice(rs.getInt("price"));
                goods.add(good);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            finish(con, statement, rs);

        }
        return goods;
    }

    @Override
    public void addGood(Good good) {
        Connection dbConnection=null;
        Statement statement=null;
        String sql="insert into good values("+good.getId()+","+good.getName() +","+good.getPrice()+","+good.getStock()+")";
        try
        {
            DataSource dataSource=new DataSource();
            dbConnection=dataSource.createConnection();
            statement=dbConnection.prepareStatement(sql);
            statement.executeUpdate(sql);
            System.out.println("Record is inserted into Good table for Good: "+good.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            finish(dbConnection, statement);
        }

    }

    @Override
    public Good getGood(int goodId) {
        DataSource dataSource=new DataSource();
        Connection connection=dataSource.createConnection();
        Statement stmt=null;
        ResultSet rs=null;
        try{
            String query="select * from good where  good_id="+goodId;
            stmt=connection.createStatement();
            rs=stmt.executeQuery(query);
            while (rs.next()){
                Good good=new Good();
                good.setId(rs.getInt("Id"));
                good.setName(rs.getString("name"));
                good.setPrice(rs.getInt("price"));
                good.setStock(rs.getInt("stock"));
                return good;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            finish(connection, stmt, rs);
        }
        return null;
    }

    private void finish(Connection connection, Statement stmt, ResultSet rs) {
        try{
            if(connection!=null){
                connection.close();
            }
            if(stmt!=null){
                stmt.close();
            }
            if(rs!=null){
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateGood(Good good) {
        Connection dbConnection=null;
        Statement statement=null;
        String sql="update good set name= "+good.getName()+" where id ="+good.getId();
        try{
            DataSource dataSource=new DataSource();
            dbConnection=dataSource.createConnection();
            statement=dbConnection.prepareStatement(sql);
            statement.executeUpdate(sql);
            System.out.println("Record is updated into good table for id :"
                    +good.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            finish(dbConnection, statement);
        }
    }

    private void finish(Connection dbConnection, Statement statement) {
        if(statement!=null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(dbConnection!=null)
        {
            try
            {
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteGood(int goodId) {
        Connection dbConnection=null;
        Statement statement=null;
        String sql="delete from good where Id="+goodId;
        try{
            DataSource dataSource=new DataSource();
            dbConnection=dataSource.createConnection();
            statement=dbConnection.prepareStatement(sql);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            finish(dbConnection,statement);
        }

    }
}

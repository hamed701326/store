package ir.store.java.object.feature.impl;

import ir.store.java.object.core.annotation.configureConnection.DataSource;
import ir.store.java.object.feature.usecase.ShoeDAO;
import ir.store.java.object.model.Shoe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShoeDAOImpl implements ShoeDAO {
    private static DataSource dataSource=new DataSource();
    @Override
    public List<Shoe> getAllShoe() {
        DataSource dataSource=new DataSource();
        Connection con=dataSource.createConnection();
        Statement statement=null;
        ResultSet rs=null;
        List<Shoe> shoeList=new ArrayList<>();
        try {
            String query="select * from shoe ";
            statement=con.createStatement();
            rs=statement.executeQuery(query);
            while (rs.next()){
                Shoe shoe=new Shoe();
                shoe.setId(rs.getInt("idshoe"));
                shoe.setName(rs.getString("name"));
                shoe.setType(rs.getString("type"));
                shoe.setSize(rs.getInt("size"));
                shoe.setStock(rs.getInt("stock"));
                shoe.setPrice(rs.getInt("price"));
                shoeList.add(shoe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {

                if(rs!=null) rs.close();
                if(statement!=null) statement.close();
                if(!con.isClosed()) con.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
        return shoeList;
    }

    @Override
    public void addShoe(Shoe shoe) {
        Connection dbConnection=null;
        PreparedStatement statement=null;
        String query="insert into shoe values (?,?,?,?,?,?)";
        try{

            dbConnection=dataSource.createConnection();
            //todo check are there any shoes with this id;if there are,update shoe with this id
            statement=dbConnection.prepareStatement(query);
            statement.setInt(1,shoe.getId());
            statement.setString(2,shoe.getName());
            statement.setString(3,shoe.getType());
            statement.setInt(4,shoe.getSize());
            statement.setInt(5,shoe.getStock());
            statement.setDouble(6,shoe.getPrice());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            finish(dbConnection,statement);
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
    public Shoe getShoe(int shoeId) {
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet rs=null;
        String query="select * from shoe where idshoe=?";
        Shoe shoe=new Shoe();
        try{
            connection=dataSource.createConnection();
            statement=connection.prepareStatement(query);
            statement.setInt(1,shoeId);
            rs=statement.executeQuery();
            if (rs.wasNull()) return null;
            shoe.setId(shoeId);
            shoe.setName(rs.getString("name"));
            shoe.setType(rs.getString("type"));
            shoe.setSize((rs.getInt("size")));
            shoe.setStock(rs.getInt("stock"));
            shoe.setPrice(rs.getInt("price"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            finish(connection,statement,rs);
        }
        return shoe;
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
    public void updateShoe(Shoe shoe) {
        Connection connection=null;
        PreparedStatement statement=null;
        if(existCheck(shoe.getId())) {
            String query = "update shoe where set name=? ,type=? ,size=?" +
                    ",stock=? ,price=?" +
                    "where  idshoe=?";
            try {
                connection = dataSource.createConnection();
                statement = connection.prepareStatement(query);
                statement.setInt(6, shoe.getId());
                statement.setString(1, shoe.getName());
                statement.setString(2, shoe.getType());
                statement.setInt(3, shoe.getSize());
                statement.setInt(4, shoe.getStock());
                statement.setDouble(5, shoe.getPrice());

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                finish(connection,statement);

            }
        }else {
            System.out.println("there aren't any shoe with id="+shoe.getId());
            System.out.print("Would you like to add this shoe? \n\t1.Yes \n\t2.No \n respone:");
            int respone=new Scanner(System.in).nextInt();
            if(respone==1){addShoe(shoe);}
        }

    }

    @Override
    public void deleteShoe(int shoeId) {
        Connection dbConnection=null;
        Statement statement=null;
        if (existCheck(shoeId)) {
            String sql = "delete from shoe where idshoe=" + shoeId;
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
    }
    public boolean existCheck(int shoeId){
        boolean exist=(getShoe(shoeId)!=null);
        if(!exist) System.out.println("There aren't any shoe with id= "+shoeId);
        return exist;
    }
}

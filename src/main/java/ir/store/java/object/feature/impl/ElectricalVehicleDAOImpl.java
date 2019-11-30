package ir.store.java.object.feature.impl;

import ir.store.java.object.core.annotation.configureConnection.DataSource;
import ir.store.java.object.feature.usecase.ElectricalVehicleDAO;
import ir.store.java.object.model.ElectricalVehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ElectricalVehicleDAOImpl implements ElectricalVehicleDAO {

    @Override
    public List<ElectricalVehicle> getAllElectricalVehicle() {
        DataSource dataSource=new DataSource();
        Connection con=dataSource.createConnection();
        Statement statement=null;
        ResultSet rs=null;
        List<ElectricalVehicle> electricalVehicles=new ArrayList<>();
        try
        {
            String query="select * from electrical_vehicle";
            statement=con.createStatement();
            rs=statement.executeQuery(query);
            while(rs.next()){
                ElectricalVehicle good=new ElectricalVehicle();
                good.setId(rs.getInt("Id"));
                good.setName(rs.getString("name"));
                good.setPower(rs.getInt("power"));
                good.setType(rs.getString("type"));
                good.setStock(rs.getInt("stock"));
                good.setPrice(rs.getInt("price"));
                electricalVehicles.add(good);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            finish(con, statement, rs);

        }
        return electricalVehicles;
    }

    public void addElectricalVehicle(ElectricalVehicle electricalVehicle) {
        Connection dbConnection=null;
        PreparedStatement statement=null;
        String sql="insert into electrical_vehicle values(?,?,?,?,?,?)";
        try
        {
            DataSource dataSource=new DataSource();
            dbConnection=dataSource.createConnection();
            statement=dbConnection.prepareStatement(sql);
            statement.setInt(1,electricalVehicle.getId());
            statement.setString(2,electricalVehicle.getName());
            statement.setString(3,electricalVehicle.getType());
            statement.setInt(4,electricalVehicle.getPower());
            statement.setInt(5,electricalVehicle.getStock());
            statement.setDouble(6,electricalVehicle.getPrice());

            statement.executeUpdate(sql);
            System.out.println("Record is inserted into electricalVehicle table for Good: "+electricalVehicle.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            finish(dbConnection, statement);
        }

    }

    @Override
    public ElectricalVehicle getElectricalVehicle(int electricalVehicleId) {
        DataSource dataSource=new DataSource();
        Connection connection=dataSource.createConnection();
        Statement stmt=null;
        ResultSet rs=null;
        try{
            String query="select * from electrical_vehicle where  id="+electricalVehicleId;
            stmt=connection.createStatement();
            rs=stmt.executeQuery(query);

                ElectricalVehicle good=new ElectricalVehicle();
                good.setId(rs.getInt("Id"));
                good.setName(rs.getString("name"));
                good.setPrice(rs.getInt("price"));
                good.setStock(rs.getInt("stock"));
                good.setPower(rs.getInt("power"));
                good.setType(rs.getString("type"));

                return good;


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
    public void updateElectricalVehicle(ElectricalVehicle good) {
        Connection dbConnection=null;
        Statement statement=null;
        String sql="update electrical_vehicle set name= "+good.getName()+" where id ="+good.getId();
        try{
            DataSource dataSource=new DataSource();
            dbConnection=dataSource.createConnection();
            statement=dbConnection.prepareStatement(sql);
            statement.executeUpdate(sql);
            System.out.println("Record is updated into electrical_vehicle table for id :"
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
    public void deleteElectricalVehicle(int electricalVehicleId) {
        Connection dbConnection=null;
        Statement statement=null;
        String sql="delete from electrical_vehicle where Id="+electricalVehicleId;
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

package ir.store.java.object.feature.impl;

import ir.store.java.object.core.annotation.configureConnection.DataSource;
import ir.store.java.object.feature.usecase.GoodDAO;
import ir.store.java.object.model.ElectricalVehicle;
import ir.store.java.object.model.Good;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ElectricalVehicleDAOImpl extends GoodDAOImpl {
    private static DataSource dataSource=new DataSource();
    private GoodDAO goodDAO=new GoodDAOImpl();
    @Override
    public List<Good> getAllGood() {
        Connection con=dataSource.createConnection();
        Statement statement=null;
        ResultSet rs=null;
        List<Good> goods=new ArrayList<>();
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
    public void addGood(Good electricalVehicle) {
        goodDAO.addGood(electricalVehicle);
        Connection dbConnection=null;
        PreparedStatement statement=null;
        if(!existCheck(electricalVehicle.getId())) {
            String sql = "insert into electrical_vehicle values(?,?,?,?,?,?)";
            try {
                DataSource dataSource = new DataSource();
                dbConnection = dataSource.createConnection();
                statement = dbConnection.prepareStatement(sql);
                statement.setInt(1, electricalVehicle.getId());
                statement.setString(2, electricalVehicle.getName());
                statement.setString(3,((ElectricalVehicle) electricalVehicle).getType());
                statement.setInt(4,((ElectricalVehicle) electricalVehicle).getPower());
                statement.setInt(5, electricalVehicle.getStock());
                statement.setDouble(6, electricalVehicle.getPrice());

                statement.executeUpdate(sql);
                System.out.println("Record is inserted into electricalVehicle table for Good: " + electricalVehicle.getName());

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                finish(dbConnection, statement);
            }
        }else {
            System.out.println("there is a shoe with id="+electricalVehicle.getId());
            System.out.print("Would you like to update this shoe? \n\t1.Yes \n\t2.No \n respone:");
            if(new Scanner(System.in).nextInt()==1) updateGood(electricalVehicle);

        }

    }

    @Override
    public Good getGood(int electricalVehicleId) {

        Connection connection=dataSource.createConnection();
        Statement stmt=null;
        ResultSet rs=null;
        try{
            String query="select * from electrical_vehicle where  id="+electricalVehicleId;
            stmt=connection.createStatement();
            rs=stmt.executeQuery(query);
            // exploring
            if(rs.wasNull()) return  null;
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


    @Override
    public void updateGood(Good good) {
        goodDAO.updateGood(good);
        Connection connection=null;
        PreparedStatement statement=null;
        if(existCheck(good.getId())) {
            String query = "update electrical_vehicle set name=?,set type=? ,set power=?" +
                    ",set stock=? , set price=? where id =" + good.getId();
            try {
                connection = dataSource.createConnection();
                statement = connection.prepareStatement(query);
                statement.setString(1, good.getName());
                statement.setString(2,((ElectricalVehicle) good).getType());
                statement.setInt(3,((ElectricalVehicle) good).getPower());
                statement.setInt(4, good.getStock());
                statement.setDouble(5, good.getPrice());
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                finish(connection, statement);
            }
        }else {
            System.out.println("There aren't any electrical vehicle with id= " + good.getId());
            System.out.print("Would you like to add this reading case? \n\t1.Yes \n\t2.No \n respone:");
            if (new Scanner(System.in).nextInt() == 1) {
                addGood(good);
            }
        }
    }


    @Override
    public void deleteGood(int electricalVehicleId) {
        goodDAO.deleteGood(electricalVehicleId);
        Connection dbConnection=null;
        Statement statement=null;
        if(existCheck(electricalVehicleId)) {
            String sql = "delete from electrical_vehicle where Id=" + electricalVehicleId;
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
        }else System.out.println("There aren't any electrical vehicle with id= "+electricalVehicleId);


}

}

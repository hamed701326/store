package ir.store.java.object.feature.impl;

import ir.store.java.object.core.annotation.configureConnection.DataSource;
import ir.store.java.object.feature.usecase.GoodDAO;
import ir.store.java.object.model.Good;
import ir.store.java.object.model.ReadingCase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadingCaseDAOImpl extends GoodDAOImpl{
    private static DataSource dataSource=new DataSource();
    private GoodDAO goodDAO=new GoodDAOImpl();

    @Override
    public List<Good> getAllGood() {
        Connection con=dataSource.createConnection();
        Statement statement=null;
        ResultSet rs=null;
        List<Good> readingCaseList=new ArrayList<>();
        try {
            String query="select * from reading_cases ";
            statement=con.createStatement();
            rs=statement.executeQuery(query);
            while (rs.next()){
                ReadingCase readingCase=new ReadingCase();
                readingCase.setId(rs.getInt("id"));
                readingCase.setName(rs.getString("name"));
                readingCase.setPublication(rs.getString("publication"));
                readingCase.setAuthor(rs.getString("author"));
                readingCase.setStock(rs.getInt("stock"));
                readingCase.setPrice(rs.getDouble("price"));
                readingCaseList.add(readingCase);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            finish(con,statement,rs);
        }
        return readingCaseList;
    }

    @Override
    public void addGood(Good readingCase) {
        goodDAO.addGood(readingCase);
        Connection dbConnection=null;
        PreparedStatement statement=null;
        String query="insert into reading_cases values (?,?,?,?,?,?)";
        if (!existCheck(readingCase.getId())) {
            try {

                dbConnection = dataSource.createConnection();

                statement = dbConnection.prepareStatement(query);
                statement.setInt(1, readingCase.getId());
                statement.setString(2, readingCase.getName());
                statement.setString(3,((ReadingCase) readingCase).getPublication());
                statement.setString(4,((ReadingCase) readingCase).getAuthor());
                statement.setInt(5, readingCase.getStock());
                statement.setDouble(6, readingCase.getPrice());
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                finish(dbConnection, statement);
            }
        }
        else {
            System.out.println("there is a shoe with id="+readingCase.getId());
            System.out.print("Would you like to update this shoe? \n\t1.Yes \n\t2.No \n respone:");
            if(new Scanner(System.in).nextInt()==1){updateGood(readingCase);}
        };
    }



    @Override
    public ReadingCase getGood(int readingCaseId) {
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet rs=null;
        String query="select * from reading_cases where id=?";
        ReadingCase readingCase=new ReadingCase();
        try{
            connection=dataSource.createConnection();
            statement=connection.prepareStatement(query);
            statement.setInt(1,readingCaseId);
            rs=statement.executeQuery();
            if (rs.wasNull()) return null;
            readingCase.setId(readingCaseId);
            readingCase.setName(rs.getString("name"));
            readingCase.setPublication(rs.getString("publication"));
            readingCase.setAuthor(rs.getString("author"));
            readingCase.setStock(rs.getInt("stock"));
            readingCase.setPrice(rs.getDouble("price"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            finish(connection,statement,rs);
        }
        return readingCase;
    }

    @Override
    public void updateGood(Good readingCase) {
        goodDAO.updateGood(readingCase);
        Connection connection=null;
        PreparedStatement statement=null;
        if(existCheck(readingCase.getId())) {
            String query = "update reading_cases where set name=? ,publication=? ,author=?" +
                    ",stock=? ,price=?" +
                    "where  idshoe=?";
            try {
                connection = dataSource.createConnection();
                statement = connection.prepareStatement(query);
                statement.setInt(6, readingCase.getId());
                statement.setString(1, readingCase.getName());
                statement.setString(2, ((ReadingCase) readingCase).getPublication());
                statement.setString(3, ((ReadingCase) readingCase).getAuthor());
                statement.setInt(4, readingCase.getStock());
                statement.setDouble(5, readingCase.getPrice());
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                finish(connection,statement);

            }
        }else {
            System.out.println("there aren't any reading case with id="+readingCase.getId());
            System.out.print("Would you like to add this reading case? \n\t1.Yes \n\t2.No \n respone:");
            if(new Scanner(System.in).nextInt()==1){addGood(readingCase);}
        }


    }

    @Override
    public void deleteGood(int readingCaseId) {
        goodDAO.deleteGood(readingCaseId);
        Connection dbConnection=null;
        Statement statement=null;
        if (existCheck(readingCaseId)) {
            String sql = "delete from reading_cases where id=" + readingCaseId;
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
        else System.out.println("There aren't any reading Case with id= "+readingCaseId);
    }

}

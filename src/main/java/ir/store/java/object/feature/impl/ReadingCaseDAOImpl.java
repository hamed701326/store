package ir.store.java.object.feature.impl;

import ir.store.java.object.core.annotation.configureConnection.DataSource;
import ir.store.java.object.feature.usecase.ReadingCaseDAO;
import ir.store.java.object.model.ReadingCase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadingCaseDAOImpl implements ReadingCaseDAO {
    private static DataSource dataSource=new DataSource();
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
    public List<ReadingCase> getAllReadingCase() {
        Connection con=dataSource.createConnection();
        Statement statement=null;
        ResultSet rs=null;
        List<ReadingCase> readingCaseList=new ArrayList<>();
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
                readingCase.setPrice(rs.getInt("price"));
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
    public void addReadingCase(ReadingCase readingCase) {
        Connection dbConnection=null;
        PreparedStatement statement=null;
        String query="insert into reading_cases values (?,?,?,?,?,?)";
        if (!existCheck(readingCase.getId())) {
            try {

                dbConnection = dataSource.createConnection();

                statement = dbConnection.prepareStatement(query);
                statement.setInt(1, readingCase.getId());
                statement.setString(2, readingCase.getName());
                statement.setString(3, readingCase.getPublication());
                statement.setString(4, readingCase.getAuthor());
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
            if(new Scanner(System.in).nextInt()==1){updateReadingCase(readingCase);}
        };
    }



    @Override
    public ReadingCase getReadingCase(int readingCaseId) {
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
            readingCase.setPrice(rs.getInt("price"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            finish(connection,statement,rs);
        }
        return readingCase;
    }

    @Override
    public void updateReadingCase(ReadingCase readingCase) {
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
                statement.setString(2, readingCase.getPublication());
                statement.setString(3, readingCase.getAuthor());
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
            if(new Scanner(System.in).nextInt()==1){updateReadingCase(readingCase);}
        }


    }

    @Override
    public void deleteReadingCase(int readingCaseId) {
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
    private boolean existCheck(int id) {
        boolean exist=(getReadingCase(id)!=null);
        return exist;
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

}

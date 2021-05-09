package com.docker.postgres;

import com.docker.pojo.SrcTbl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DockerPostgresConn {
    public List<SrcTbl> getSrcTbl (){
        List<SrcTbl> result = new ArrayList<>();

        String SQL_SELECT = "Select * from src_tbl";

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5433/src_postgres", "postgres", "postgres");
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String creation_date = resultSet.getString("creation_date");
                String sale_value = resultSet.getString("sale_value");

                SrcTbl srcTbl = new SrcTbl(id,creation_date,sale_value);
                System.out.println(id+":"+creation_date+":"+sale_value);
                result.add(srcTbl);

            }
            result.forEach(x -> System.out.println(x));

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public void insertIntoTgt(List<SrcTbl> srcTblList){
        String SQL = "INSERT INTO tgt_tbl(id,creation_date,sale_value) " + "VALUES(?,?,?)";
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5434/tgt_postgres", "postgres", "postgres");
             PreparedStatement preparedStatement = conn.prepareStatement(SQL)) {
            int count = 0;
            System.out.println(conn.getMetaData().getDriverName());
            System.out.println(conn.getMetaData().getURL());

            for (SrcTbl srcTbl : srcTblList) {
                preparedStatement.setInt(1,srcTbl.getId());
                preparedStatement.setString(2,srcTbl.getCreation_date());
                preparedStatement.setString(3,srcTbl.getSale_value());
                preparedStatement.addBatch();
                count++;
               
                if (count % 100 == 0 || count == srcTblList.size()) {
                    preparedStatement.executeBatch();
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String args[]){
        DockerPostgresConn dockerPostgresConn = new DockerPostgresConn();
        List<SrcTbl> res = dockerPostgresConn.getSrcTbl();
        dockerPostgresConn.insertIntoTgt(res);


    }
}

package com.sccs.model.dao;

import com.sccs.model.domain.SwimmingPool;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SwimmingPoolDAO extends DataAcessObject {

    public static boolean insert(SwimmingPool pool) {

        String sql = "INSERT INTO swimmingPool(poolName, averageAge, maxCapacity," +
            " lanesNumber, poolWidth, poolLength, poolDepth)" +
            " VALUES(?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setString(1, pool.getName());
            statement.setInt(2, pool.getAverageAge());
            statement.setInt(3, pool.getMaxCapacity());
            statement.setInt(4, pool.getLanesNumber());
            statement.setDouble(5, pool.getWidth());
            statement.setDouble(6, pool.getLength());
            statement.setDouble(7, pool.getDepth());
            statement.execute();
            return true;
        }
        catch (SQLException sqlException) {
            Logger.getLogger(SwimmingPoolDAO.class.getName()).log(Level.SEVERE, null, sqlException);
            return false;
        }
    }

    public static boolean update(SwimmingPool pool) {
        String sql = "UPDATE swimmingPool SET " +
            "poolName=?, averageAge=?, maxCapacity=?, lanesNumber=?, " +
            "poolWidth=?, poolLength=?, poolDepth=? WHERE poolNumber=?";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setString(1, pool.getName());
            statement.setInt(2, pool.getAverageAge());
            statement.setInt(3, pool.getMaxCapacity());
            statement.setInt(4, pool.getLanesNumber());
            statement.setDouble(5, pool.getWidth());
            statement.setDouble(6, pool.getLength());
            statement.setDouble(7, pool.getDepth());
            statement.setInt(8, pool.getNumber());
            statement.execute();
            return true;
        }
        catch (SQLException sqlException) {
            Logger.getLogger(SwimmingPoolDAO.class.getName()).log(Level.SEVERE, null, sqlException);
            return false;
        }
    }

    public static void delete(SwimmingPool pool) throws SQLException {

        String sql = "DELETE FROM swimmingPool WHERE poolNumber=?";
        PreparedStatement statement = connectionDAO.prepareStatement(sql);
        statement.setInt(1, pool.getNumber());
        statement.execute();
    }

    public static List<SwimmingPool> list() {

        String sql = "SELECT * FROM swimmingPool";
        List<SwimmingPool> poolList = new ArrayList<>();
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                SwimmingPool pool = new SwimmingPool(
                    resultSet.getInt("poolNumber"),
                    resultSet.getString("poolName"),
                    resultSet.getInt("averageAge"),
                    resultSet.getInt("maxCapacity"),
                    resultSet.getInt("lanesNumber"),
                    resultSet.getDouble("poolWidth"),
                    resultSet.getDouble("poolLength"),
                    resultSet.getDouble("poolDepth")
                );
                poolList.add(pool);
            }
        }
        catch (SQLException sqlException) {
            Logger.getLogger(SwimmingPoolDAO.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        return poolList;
    }

    public static SwimmingPool search(Integer number) {

        String sql = "SELECT * FROM swimmingPool WHERE poolNumber=?";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setInt(1, number);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new SwimmingPool(
                    resultSet.getInt("poolNumber"),
                    resultSet.getString("poolName"),
                    resultSet.getInt("averageAge"),
                    resultSet.getInt("maxCapacity"),
                    resultSet.getInt("lanesNumber"),
                    resultSet.getDouble("poolWidth"),
                    resultSet.getDouble("poolLength"),
                    resultSet.getDouble("poolDepth")
                );
            }
        }
        catch (SQLException sqlException) {
            Logger.getLogger(SwimmingPoolDAO.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        return null;
    }
}
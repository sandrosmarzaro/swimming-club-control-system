package com.sccs.model.dao;

import com.sccs.model.domain.Enroll;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnrollDAO extends DataAcessObject {

    public boolean insert(Enroll enroll) {

        String sql = "INSERT INTO enroll(classId, employee, student) " +
                "VALUES(?, ?, ?)";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setInt(1, enroll.getClassId());
            statement.setInt(2, enroll.getEmployeeId());
            statement.setInt(3, enroll.getStudentId());
            statement.execute();
            return true;
        }
        catch (SQLException sqlException) {
            Logger.getLogger(EnrollDAO.class.getName()).log(Level.SEVERE, null, sqlException);
            return false;
        }
    }

    public boolean update(Enroll enroll) {
        String sql = "UPDATE enroll SET " +
                "enroll(classId, employee, student) " +
                "VALUES(?, ?, ?) WHERE enrollId=?";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setInt(1, enroll.getClassId());
            statement.setInt(2, enroll.getEmployeeId());
            statement.setInt(3, enroll.getStudentId());
            statement.setInt(4, enroll.getId());
            statement.execute();
            return true;
        }
        catch (SQLException sqlException) {
            Logger.getLogger(EnrollDAO.class.getName()).log(Level.SEVERE, null, sqlException);
            return false;
        }
    }

    public boolean delete(Enroll enroll) {

        String sql = "DELETE FROM enroll WHERE enrollId=?";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setInt(1, enroll.getId());
            statement.execute();
            return true;
        }
        catch (SQLException sqlException) {
            Logger.getLogger(EnrollDAO.class.getName()).log(Level.SEVERE, null, sqlException);
            return false;
        }
    }

    public List<Enroll> list() {

        String sql = "SELECT * FROM enroll";
        List<Enroll> enrollList = new ArrayList<>();
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Enroll enroll = new Enroll(
                        resultSet.getInt("enrollId"),
                        resultSet.getInt("classId"),
                        resultSet.getInt("employee"),
                        resultSet.getInt("student")
                );
                enrollList.add(enroll);
            }
        }
        catch (SQLException sqlException) {
            Logger.getLogger(EnrollDAO.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        return enrollList;
    }

    public Enroll search(Integer number) {

        String sql = "SELECT * FROM enroll WHERE enrollId=?";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setInt(1, number);
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return new Enroll(
                        resultSet.getInt("enrollId"),
                        resultSet.getInt("classId"),
                        resultSet.getInt("employee"),
                        resultSet.getInt("student")
                );
            }
        }
        catch (SQLException sqlException) {
            Logger.getLogger(EnrollDAO.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        return null;
    }
}
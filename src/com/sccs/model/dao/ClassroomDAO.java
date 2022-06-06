package com.sccs.model.dao;

import com.sccs.model.domain.Classroom;
import com.sccs.model.domain.DayOfTheWeek;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClassroomDAO extends DataAcessObject {

    public static boolean insert(Classroom classroom) {

        String sql = "INSERT INTO classroom(className, usedPool, vacanciesNumber, " +
                "enrollmentOpen, teacher, dayOfTheWeek) " +
                "VALUES(?, ?, ?, ?, ?, ?::day_of_the_week)";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setString(1, classroom.getName());
            statement.setInt(2, classroom.getPoolId());
            statement.setInt(3, classroom.getVacanciesNumber());
            statement.setBoolean(4, classroom.getEnrollmentOpen());
            statement.setInt(5, classroom.getTeacherId());
            statement.setString(6, classroom.getDayOfTheWeek().toString());
            statement.execute();
            return true;
        }
        catch (SQLException sqlException) {
            Logger.getLogger(ClassroomDAO.class.getName()).log(Level.SEVERE, null, sqlException);
            return false;
        }
    }

    public static boolean update(Classroom classroom) {
        String sql = "UPDATE classroom SET " +
                "className=?, usedPool=?, enrollmentOpen=?, " +
                "teacher=?, dayOfTheWeek=?::day_of_the_week " +
                "WHERE classroomId=?";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setString(1, classroom.getName());
            statement.setInt(2, classroom.getPoolId());
            statement.setBoolean(3, classroom.getEnrollmentOpen());
            statement.setInt(4, classroom.getTeacherId());
            statement.setString(5, classroom.getDayOfTheWeek().toString());
            statement.setInt(6, classroom.getId());
            statement.execute();
            return true;
        }
        catch (SQLException sqlException) {
            Logger.getLogger(ClassroomDAO.class.getName()).log(Level.SEVERE, null, sqlException);
            return false;
        }
    }

    public static boolean delete(Classroom classroom) {

        String sql = "DELETE FROM classroom WHERE classroomId=?";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setInt(1, classroom.getId());
            statement.execute();
            return true;
        }
        catch (SQLException sqlException) {
            Logger.getLogger(ClassroomDAO.class.getName()).log(Level.SEVERE, null, sqlException);
            return false;
        }
    }

    public static List<Classroom> list() {

        String sql = "SELECT * FROM classroom";
        List<Classroom> classList = new ArrayList<>();
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Classroom classroom = new Classroom(
                    resultSet.getInt("classroomId"),
                    resultSet.getString("className"),
                    resultSet.getInt("usedPool"),
                    resultSet.getInt("vacanciesNumber"),
                    resultSet.getBoolean("enrollmentOpen"),
                    resultSet.getInt("teacher"),
                    DayOfTheWeek.valueOf(resultSet.getString("dayOfTheWeek"))
                );
                classList.add(classroom);
            }
        }
        catch (SQLException sqlException) {
            Logger.getLogger(ClassroomDAO.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        return classList;
    }
    
    public static List<String> listNames() {
        String sql = "SELECT className FROM classroom;";
        List<String> namesList = new ArrayList<>();
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                namesList.add(resultSet.getString("className"));
            }
        }
        catch (SQLException sqlException) {
            Logger.getLogger(ClassroomDAO.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        return namesList;
    }

    public static Classroom search(Integer number) {

        String sql = "SELECT * FROM classroom WHERE classroomId=?";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setInt(1, number);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Classroom(
                    resultSet.getInt("classroomId"),
                    resultSet.getString("className"),
                    resultSet.getInt("usedPool"),
                    resultSet.getInt("vacanciesNumber"),
                    resultSet.getBoolean("enrollmentOpen"),
                    resultSet.getInt("teacher"),
                    DayOfTheWeek.valueOf(resultSet.getString("dayOfTheWeek"))
                );
            }
        }
        catch (SQLException sqlException) {
            Logger.getLogger(ClassroomDAO.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        return null;
    }
    
    public static boolean decreaseVacancies(Integer id) {
        
        String sql = "UPDATE classroom SET " +
            "vacanciesNumber = vacanciesNumber - 1\n" +
            "WHERE classroomId = ?;";
        try{
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();
            return true;
        }
        catch (SQLException sqlException) {
            Logger.getLogger(ClassroomDAO.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        return false;
    }
    
    public static boolean increaseVacancies(Integer id) {
        
        String sql = "UPDATE classroom SET " +
            "vacanciesNumber = vacanciesNumber + 1\n" +
            "WHERE classroomId = ?;";
        try{
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();
            return true;
        }
        catch (SQLException sqlException) {
            Logger.getLogger(ClassroomDAO.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        return false;
    }
    
    public static boolean hasVacancy(Integer id) {
        
        String sql = "SELECT vacanciesnumber FROM classroom WHERE classroomid=?";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt("vacanciesNumber") > 0;
            }
        }
        catch (SQLException sqlException) {
            Logger.getLogger(ClassroomDAO.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        return false;
    }
}
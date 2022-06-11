package com.sccs.model.dao;

import com.sccs.model.domain.Teacher;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeacherDAO extends DataAcessObject {

    public static void insert(Teacher teacher) throws SQLException {

        String sql = "INSERT INTO teacher(teacherCpf, teacherName) VALUES(?, ?)";
        PreparedStatement statement = connectionDAO.prepareStatement(sql);
        statement.setString(1, teacher.getCpf());
        statement.setString(2, teacher.getName());
        statement.execute();
    }

    public static void update(Teacher teacher) throws SQLException {

        String sql = "UPDATE teacher SET teacherCpf=?, teacherName=? WHERE teacherId=?";
        PreparedStatement statement = connectionDAO.prepareStatement(sql);
        statement.setString(1, teacher.getCpf());
        statement.setString(2, teacher.getName());
        statement.setInt(3, teacher.getId());
        statement.execute();
    }

    public static void delete(Teacher teacher) throws SQLException {

        String sql = "DELETE FROM teacher WHERE teacherCpf=?";
        PreparedStatement statement = connectionDAO.prepareStatement(sql);
        statement.setString(1, teacher.getCpf());
        statement.execute();
    }

    public static List<Teacher> list() {

        String sql = "SELECT * FROM teacher";
        List<Teacher> teacherList = new ArrayList<>();
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Teacher teacher = new Teacher(
                    resultSet.getInt("teacherId"),
                    resultSet.getString("teacherCpf"),
                    resultSet.getString("teacherName")
                );
                teacherList.add(teacher);
            }
        }
        catch (SQLException sqlException) {
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        return teacherList;
    }

    public static Teacher search(Integer id) {

        String sql = "SELECT * FROM teacher WHERE teacherId=?";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Teacher(
                    resultSet.getInt("teacherId"),
                    resultSet.getString("teacherCpf"),
                    resultSet.getString("teacherName")
                );
            }
        }
        catch (SQLException sqlException) {
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        return null;
    }
}

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

    public boolean insert(Teacher teacher) {

        String sql = "INSERT INTO teacher(teacherCpf, teacherName) VALUES(?, ?)";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setString(1, teacher.getCpf());
            statement.setString(2, teacher.getName());
            statement.execute();
            return true;
        }
        catch (SQLException sqlException) {
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, sqlException);
            return false;
        }
    }

    public boolean update(Teacher teacher) {

        String sql = "UPDATE teacher SET teacherCpf=?, teacherName=? WHERE teacherCpf=?";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setString(1, teacher.getCpf());
            statement.setString(2, teacher.getName());
            statement.setString(3, teacher.getCpf());
            statement.execute();
            return true;
        }
        catch (SQLException sqlException) {
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, sqlException);
            return false;
        }
    }

    public boolean delete(Teacher teacher) {

        String sql = "DELETE FROM teacher WHERE teacherCpf=?";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setString(1, teacher.getCpf());
            statement.execute();
            return true;
        }
        catch (SQLException sqlException) {
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, sqlException);
            return false;
        }
    }

    public List<Teacher> list() {

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

    public Teacher search(String cpf) {

        String sql = "SELECT * FROM teacher WHERE teacherCpf=?";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setString(1, cpf);
            ResultSet resultSet = statement.executeQuery(sql);
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

package com.sccs.model.dao;

import com.sccs.model.domain.Student;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentDAO extends DataAcessObject {

    public boolean insert(Student student) {

        String sql = "INSERT INTO student(studentCpf, studentName, age) VALUES(?, ?, ?)";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setString(1, student.getCpf());
            statement.setString(2, student.getName());
            statement.setDate(3, Date.valueOf(student.getAge()));
            statement.execute();
            return true;
        }
        catch (SQLException sqlException) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, sqlException);
            return false;
        }
    }

    public boolean update(Student student) {

        String sql = "UPDATE student SET studentCpf=?, studentName=?, age=? WHERE studentCpf=?";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setString(1, student.getCpf());
            statement.setString(2, student.getName());
            statement.setDate(3, Date.valueOf(student.getAge()));
            statement.setString(4, student.getCpf());
            statement.execute();
            return true;
        }
        catch (SQLException sqlException) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, sqlException);
            return false;
        }
    }

    public boolean delete(Student student) {

        String sql = "DELETE FROM student WHERE studentCpf=?";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setString(1, student.getCpf());
            statement.execute();
            return true;
        }
        catch (SQLException sqlException) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, sqlException);
            return false;
        }
    }

    public List<Student> list() {

        String sql = "SELECT * FROM student";
        List<Student> studentList = new ArrayList<>();
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Student student = new Student(
                        resultSet.getInt("studentId"),
                        resultSet.getString("studentCpf"),
                        resultSet.getString("studentName"),
                        resultSet.getDate("age").toLocalDate()
                );
                studentList.add(student);
            }
        }
        catch (SQLException sqlException) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        return studentList;
    }

    public Student search(String cpf) {

        String sql = "SELECT * FROM student WHERE studentCpf=?";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setString(1, cpf);
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return new Student(
                        resultSet.getInt("studentId"),
                        resultSet.getString("studentCpf"),
                        resultSet.getString("studentName"),
                        resultSet.getDate("age").toLocalDate()
                );
            }
        }
        catch (SQLException sqlException) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        return null;
    }
}

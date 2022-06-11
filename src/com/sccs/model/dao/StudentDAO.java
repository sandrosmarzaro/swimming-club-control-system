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

    public static void insert(Student student) throws SQLException {

        String sql = "INSERT INTO student(studentCpf, studentName, birthDate, age) VALUES(?, ?, ?, ?)";
        PreparedStatement statement = connectionDAO.prepareStatement(sql);
        statement.setString(1, student.getCpf());
        statement.setString(2, student.getName());
        statement.setDate(3, Date.valueOf(student.getBirthDate()));
        statement.setInt(4, student.getAge());
        statement.execute();
    }

    public static void update(Student student) throws SQLException {

        String sql = "UPDATE student SET studentCpf=?, studentName=?, birthDate=?, age=? WHERE studentId=?";
        PreparedStatement statement = connectionDAO.prepareStatement(sql);
        statement.setString(1, student.getCpf());
        statement.setString(2, student.getName());
        statement.setDate(3, Date.valueOf(student.getBirthDate()));
        statement.setInt(4, student.getAge());
        statement.setInt(5, student.getId());
        statement.execute();
    }

    public static void delete(Student student) throws SQLException {

        String sql = "DELETE FROM student WHERE studentCpf=?";
        PreparedStatement statement = connectionDAO.prepareStatement(sql);
        statement.setString(1, student.getCpf());
        statement.execute();
    }

    public static List<Student> list() {

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
                    resultSet.getDate("birthDate").toLocalDate()
                );
                studentList.add(student);
            }
        }
        catch (SQLException sqlException) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        return studentList;
    }

    public static Student search(String cpf) {

        String sql = "SELECT * FROM student WHERE studentCpf=?";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setString(1, cpf);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Student(
                    resultSet.getInt("studentId"),
                    resultSet.getString("studentCpf"),
                    resultSet.getString("studentName"),
                    resultSet.getDate("birthDate").toLocalDate()
                );
            }
        }
        catch (SQLException sqlException) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        return null;
    }
    
    public static Student search(Integer id) {

        String sql = "SELECT * FROM student WHERE studentId=?";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Student(
                    resultSet.getInt("studentId"),
                    resultSet.getString("studentCpf"),
                    resultSet.getString("studentName"),
                    resultSet.getDate("birthDate").toLocalDate()
                );
            }
        }
        catch (SQLException sqlException) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        return null;
    }
}

package com.sccs.model.dao;

import static com.sccs.model.dao.DataAcessObject.connectionDAO;
import com.sccs.model.domain.Employee;
import com.sccs.model.domain.Encryptor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeDAO extends DataAcessObject {

    public static void insert(Employee employee) throws SQLException {
        
        String sql = "INSERT INTO employee(" +
            "employeeCpf, employeeName, employeeLogin, employeePassword) " +
            "VALUES(?, ?, ?, ?)";
        PreparedStatement statement = connectionDAO.prepareStatement(sql);
        statement.setString(1, employee.getCpf());
        statement.setString(2, employee.getName());
        statement.setString(3, employee.getLogin());
        String encryptedPassword = Encryptor.encryptPassword(employee.getPassword());
        statement.setString(4, encryptedPassword);
        statement.execute();
    }

    public static void update(Employee employee) throws SQLException {
        
        String sql = "UPDATE employee SET employeeCpf=?, employeeName=?, "
            + "employeeLogin=? WHERE employeeId=?";
        PreparedStatement statement = connectionDAO.prepareStatement(sql);
        statement.setString(1, employee.getCpf());
        statement.setString(2, employee.getName());
        statement.setString(3, employee.getLogin());
        statement.setInt(4, employee.getId());
        statement.execute();
    }

    public static void delete(Employee employee) throws SQLException {
        
        String sql = "DELETE FROM employee WHERE employeeCpf=?";
        
        PreparedStatement statement = connectionDAO.prepareStatement(sql);
        statement.setString(1, employee.getCpf());
        statement.execute();
    }

    public static List<Employee> list() {
        
        String sql = "SELECT * FROM employee";
        List<Employee> employeeList = new ArrayList<>();
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Employee employee = new Employee(
                    resultSet.getInt("employeeId"),
                    resultSet.getString("employeeCpf"),
                    resultSet.getString("employeeName"),
                    resultSet.getString("employeeLogin"),
                    resultSet.getString("employeePassword")
                );
                employeeList.add(employee);
            }
        }
        catch (SQLException sqlException) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        return employeeList;
    }

    public static Employee cpfSearch(String cpf) {
        
        String sql = "SELECT * FROM employee WHERE employeeCpf=?";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setString(1, cpf);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Employee(
                    resultSet.getInt("employeeId"),
                    resultSet.getString("employeeCpf"),
                    resultSet.getString("employeeName"),
                    resultSet.getString("employeeLogin"),
                    resultSet.getString("employeePassword")
                );
            }
        }
        catch (SQLException sqlException) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        return null;
    }
    
    public static Employee loginSearch(String login) {
        
        String sql = "SELECT * FROM employee WHERE employeeLogin=?";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Employee(
                    resultSet.getInt("employeeId"),
                    resultSet.getString("employeeCpf"),
                    resultSet.getString("employeeName"),
                    resultSet.getString("employeeLogin"),
                    resultSet.getString("employeePassword")
                );
            }
        }
        catch (SQLException sqlException) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        return null;
    }
    
    public static Employee idSearch(Integer id) {
        
        String sql = "SELECT * FROM employee WHERE employeeId=?";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Employee(
                    resultSet.getInt("employeeId"),
                    resultSet.getString("employeeCpf"),
                    resultSet.getString("employeeName"),
                    resultSet.getString("employeeLogin"),
                    resultSet.getString("employeePassword")
                );
            }
        }
        catch (SQLException sqlException) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        return null;
    }
    
    public static Boolean signIn(String login, String password) {
        String sql = "SELECT * FROM employee WHERE employeeLogin=?";
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Employee employee = new Employee(
                    resultSet.getInt("employeeId"),
                    resultSet.getString("employeeCpf"),
                    resultSet.getString("employeeName"),
                    resultSet.getString("employeeLogin"),
                    resultSet.getString("employeePassword")
                );
                return Encryptor.isCorrectPassword(password, employee.getPassword());
            }
            return false;
        }
        catch (SQLException sqlException) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        return false;
    }
}

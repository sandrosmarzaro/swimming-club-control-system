package com.sccs.model.dao;

import static com.sccs.model.dao.DataAcessObject.connectionDAO;
import com.sccs.model.domain.EnrollTable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class EnrollTableDAO extends DataAcessObject {
    
    public static List<EnrollTable> list() {
        
        String sql = "SELECT enroll.enrollid AS enrollId, " +
            "student.studentname AS studentName, " +
            "classroom.className AS classname, " +
            "employee.employeename AS employeeName " +
            "FROM enroll " +
            "INNER JOIN student ON enroll.student = student.studentid " +
            "INNER JOIN classroom ON enroll.classid = classroom.classroomid " +
            "INNER JOIN employee ON enroll.employee = employee.employeeid;";
        List<EnrollTable> list = new ArrayList<>();;
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                EnrollTable enrollTable = new EnrollTable(
                    resultSet.getInt("enrollId"),
                    resultSet.getString("studentName"),
                    resultSet.getString("classname"),
                    resultSet.getString("employeeName")
                );
                list.add(enrollTable);
            }
        }
        catch (SQLException sqlException) {
            Logger.getLogger(EnrollTableDAO.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        return list;
    }
    
}

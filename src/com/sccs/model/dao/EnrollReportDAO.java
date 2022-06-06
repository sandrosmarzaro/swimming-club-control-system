package com.sccs.model.dao;

import static com.sccs.model.dao.DataAcessObject.connectionDAO;
import com.sccs.model.domain.DayOfTheWeek;
import com.sccs.model.domain.EnrollReport;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnrollReportDAO extends DataAcessObject {
    
    public static List<EnrollReport> list(Integer minAge, Integer maxAge) {
        String sql = "SELECT\n" +
            "  COUNT(enroll.enrollid) AS studentsQuantity,\n" +
            "  SUM (classroom.vacanciesnumber) AS vacanciesQuantity,\n" +
            "  TRUNC (AVG (pool.averageage), 2) AS averageAge,\n" +
            "  classroom.dayoftheweek AS dayWeek\n" +
            "FROM\n" +
            "  enroll\n" +
            "  INNER JOIN classroom ON enroll.classid = classroom.classroomid\n" +
            "  INNER JOIN swimmingpool pool ON classroom.usedpool = pool.poolnumber\n" +
            "  WHERE pool.averageage > "+ minAge + " AND pool.averageage < " + maxAge +"\n" +
            "GROUP BY\n" +
            "  classroom.dayoftheweek;";
        List<EnrollReport> list = new ArrayList();
        try {
            PreparedStatement statement = connectionDAO.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                EnrollReport enrollReport = new EnrollReport(
                    resultSet.getInt("studentsQuantity"),
                    resultSet.getInt("vacanciesQuantity"),
                    resultSet.getDouble("averageAge"),
                    DayOfTheWeek.valueOf(resultSet.getString("dayWeek"))
                );
                list.add(enrollReport);
            }
        }
        catch (SQLException sqlException) {
            Logger.getLogger(EnrollReportDAO.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        return list;
    }
}

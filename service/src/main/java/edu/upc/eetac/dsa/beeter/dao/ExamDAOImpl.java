package edu.upc.eetac.dsa.beeter.dao;

import edu.upc.eetac.dsa.beeter.db.Database;
import edu.upc.eetac.dsa.beeter.entity.Exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExamDAOImpl implements ExamDAO
{
    @Override
    public Exam createExam(String userid, String subject, String text, String statement_url) throws SQLException
    {
        Connection        connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(UserDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            stmt = connection.prepareStatement(ExamDAOQuery.CREATE_EXAM);
            stmt.setString(1, id);
            stmt.setString(2, userid);
            stmt.setString(3, subject);
            stmt.setString(4, text);
            stmt.setString(5, statement_url);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return getExamById(id);
    }

    @Override
    public Exam getExamById(String id) throws SQLException {
        Exam exam = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ExamDAOQuery.GET_EXAM_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                exam = new Exam();
                exam.setId(rs.getString("id"));
                exam.setUserid(rs.getString("userid"));
                exam.setSubject(rs.getString("subject"));
                exam.setText(rs.getString("text"));
                exam.setStatement_url(rs.getString("statement_url"));
                exam.setCreated_at(rs.getTimestamp("created_at").getTime());
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return exam;
    }

}

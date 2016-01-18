package edu.upc.eetac.dsa.beeter.dao;

import edu.upc.eetac.dsa.beeter.db.Database;
import edu.upc.eetac.dsa.beeter.entity.Subject;
import edu.upc.eetac.dsa.beeter.entity.SubjectCollection;

import java.sql.*;

public class SubjectDAOImpl implements SubjectDAO
{
    @Override
    public Subject createSubject(String name) throws SQLException
    {
        Connection        connection = null;
        PreparedStatement stmt       = null;
        String            id         = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(UserDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            stmt = connection.prepareStatement(SubjectDAOQuery.CREATE_SUBJECT);
            stmt.setString(1, id);
            stmt.setString(2, name);
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
        return getSubjectById(id);
    }

    @Override
    public Subject getSubjectById(String id) throws SQLException
    {
        Subject subject = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(SubjectDAOQuery.GET_SUBJECT_BY_ID);
            stmt.setString(1, id);


            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                subject = new Subject();
                subject.setId(rs.getString("id"));
                subject.setName(rs.getString("name"));
                subject.setCreated_at(rs.getTimestamp("created_at").getTime());
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return subject;
    }

    @Override
    public SubjectCollection getSubjects(long timestamp, boolean before) throws SQLException
    {
        SubjectCollection subjectCollection = new SubjectCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            if (before)
                stmt = connection.prepareStatement(SubjectDAOQuery.GET_SUBJECTS);
            else
                stmt = connection.prepareStatement(SubjectDAOQuery.GET_SUBJECTS_AFTER);
            stmt.setTimestamp(1, new Timestamp(timestamp));

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getString("id"));
                subject.setName(rs.getString("name"));
                subject.setCreated_at(rs.getTimestamp("created_at").getTime());

                if (first) {
                    subjectCollection.setNewestTimestamp(subject.getCreated_at());
                    first = false;
                }
                subjectCollection.setOldestTimestamp(subject.getCreated_at());
                subjectCollection.getSubjects().add(subject);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return subjectCollection;
    }
}

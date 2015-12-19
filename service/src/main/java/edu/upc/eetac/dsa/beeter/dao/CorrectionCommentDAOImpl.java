package edu.upc.eetac.dsa.beeter.dao;

import edu.upc.eetac.dsa.beeter.db.Database;
import edu.upc.eetac.dsa.beeter.entity.CorrectionComment;
import edu.upc.eetac.dsa.beeter.entity.CorrectionCommentCollection;

import java.sql.*;

public class CorrectionCommentDAOImpl implements CorrectionCommentDAO
{
    @Override
        public CorrectionComment createCorrectionComment(String user_id, String exam_id, String correction_id,String text) throws SQLException
        {
            Connection connection = null;
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

                stmt = connection.prepareStatement(CorrectionCommentDAOQuery.CREATE_CORRECTION_COMMENT);
                stmt.setString(1, id);
                stmt.setString(2, user_id);
                stmt.setString(3, exam_id);
                stmt.setString(4, correction_id);
                stmt.setString(5, text);
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
            return getCorrectionCommentById(id);
        }

        @Override
        public CorrectionComment getCorrectionCommentById(String id) throws SQLException {
            CorrectionComment correction_comment = null;

            Connection connection = null;
            PreparedStatement stmt = null;
            try {
                connection = Database.getConnection();

                stmt = connection.prepareStatement(CorrectionCommentDAOQuery.GET_CORRECTION_COMMENT_BY_ID);
                stmt.setString(1, id);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    correction_comment = new CorrectionComment();
                    correction_comment.setId(rs.getString("id"));
                    correction_comment.setUser_id(rs.getString("user_id"));
                    correction_comment.setExam_id(rs.getString("exam_id"));
                    correction_comment.setCorrection_id(rs.getString("correction_id"));
                    correction_comment.setText(rs.getString("text"));
                    correction_comment.setCreated_at(rs.getTimestamp("created_at").getTime());
                }
            } catch (SQLException e) {
                throw e;
            } finally {
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            }
            return correction_comment;
        }

    @Override
    public CorrectionCommentCollection getComments(long timestamp, boolean before) throws SQLException {
        CorrectionCommentCollection commentCollection = new CorrectionCommentCollection();
        CorrectionComment correction_comment = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            if (before)
                stmt = connection.prepareStatement(CorrectionCommentDAOQuery.GET_CORRECTION_COMMENTS);
            else
                stmt = connection.prepareStatement(CorrectionCommentDAOQuery.GET_CORRECTION_COMMENTS_AFTER);
            stmt.setTimestamp(1, new Timestamp(timestamp));

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                correction_comment = new CorrectionComment();
                correction_comment.setId(rs.getString("id"));
                correction_comment.setUser_id(rs.getString("user_id"));
                correction_comment.setExam_id(rs.getString("exam_id"));
                correction_comment.setCorrection_id(rs.getString("correction_id"));
                correction_comment.setText(rs.getString("text"));
                correction_comment.setCreated_at(rs.getTimestamp("created_at").getTime());
                if (first) {
                    commentCollection.setNewestTimestamp(correction_comment.getCreated_at());
                    first = false;
                }
                commentCollection.setOldestTimestamp(correction_comment.getCreated_at());
                commentCollection.getCorrectioncomments().add(correction_comment);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return commentCollection;
    }


}

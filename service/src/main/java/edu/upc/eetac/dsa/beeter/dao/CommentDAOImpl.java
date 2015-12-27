package edu.upc.eetac.dsa.beeter.dao;

import edu.upc.eetac.dsa.beeter.db.Database;
import edu.upc.eetac.dsa.beeter.entity.Comment;
import edu.upc.eetac.dsa.beeter.entity.CommentCollection;

import java.sql.*;

public class CommentDAOImpl implements CommentDAO
{
    @Override
    public Comment createComment(String user_id, String exam_id, String text) throws SQLException
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

            stmt = connection.prepareStatement(CommentDAOQuery.CREATE_COMMENT);
            stmt.setString(1, id);
            stmt.setString(2, user_id);
            stmt.setString(3, exam_id);
            stmt.setString(4, text);
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
        return getCommentById(id);
    }

    @Override
    public Comment getCommentById(String id) throws SQLException {
        Comment comment = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(CommentDAOQuery.GET_COMMENT_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                comment = new Comment();
                comment.setId(rs.getString("id"));
                comment.setUser_id(rs.getString("user_id"));
                comment.setExam_id(rs.getString("exam_id"));
                comment.setText(rs.getString("text"));
                comment.setCreated_at(rs.getTimestamp("created_at").getTime());
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return comment;
    }

    @Override
    public CommentCollection getComments(String exam_id, long timestamp, boolean before) throws SQLException {
        CommentCollection commentCollection = new CommentCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            if (before)
                stmt = connection.prepareStatement(CommentDAOQuery.GET_COMMENTS);
            else
                stmt = connection.prepareStatement(CommentDAOQuery.GET_COMMENTS_AFTER);
            stmt.setTimestamp(1, new Timestamp(timestamp));
            stmt.setString(2, exam_id);

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getString("id"));
                comment.setUser_id(rs.getString("user_id"));
                comment.setExam_id(rs.getString("exam_id"));
                comment.setText(rs.getString("text"));
                comment.setCreated_at(rs.getTimestamp("created_at").getTime());
                if (first) {
                    commentCollection.setNewestTimestamp(comment.getCreated_at());
                    first = false;
                }
                commentCollection.setOldestTimestamp(comment.getCreated_at());
                commentCollection.getComments().add(comment);
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

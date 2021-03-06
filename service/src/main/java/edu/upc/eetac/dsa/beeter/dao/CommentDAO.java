package edu.upc.eetac.dsa.beeter.dao;

import edu.upc.eetac.dsa.beeter.entity.Comment;
import edu.upc.eetac.dsa.beeter.entity.CommentCollection;

import java.sql.SQLException;

public interface CommentDAO
{
    public Comment createComment(String user_id, String exam_id, String text) throws SQLException;
    public Comment getCommentById(String id) throws SQLException;
    public boolean deleteComment(String id) throws SQLException;
    public CommentCollection getComments(String id, long timestamp, boolean before) throws SQLException;
}

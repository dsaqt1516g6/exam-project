package edu.upc.eetac.dsa.beeter.dao;

import edu.upc.eetac.dsa.beeter.entity.Comment;

import java.sql.SQLException;

public interface CommentDAO
{
    public Comment createComment(String user_id, String exam_id, String text) throws SQLException;
    public Comment getCommentById(String id) throws SQLException;
}

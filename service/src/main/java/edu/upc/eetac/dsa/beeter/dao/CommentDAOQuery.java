package edu.upc.eetac.dsa.beeter.dao;

public class CommentDAOQuery
{
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_COMMENT = "insert into comment_exam (id,  user_id, exam_id, text) values (UNHEX(?), UNHEX(?), UNHEX(?), ?)";
    public final static String GET_COMMENT_BY_ID = "select hex(s.id) as id, hex(s.user_id) as user_id, s.text, hex(s.exam_id) as exam_id, s.created_at from comment_exam s, users u where s.id=unhex(?) and u.id=s.user_id";

}

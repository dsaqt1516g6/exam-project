package edu.upc.eetac.dsa.beeter.dao;

public class CommentDAOQuery
{
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_COMMENT = "insert into comment_exam (id,  user_id, exam_id, text) values (UNHEX(?), UNHEX(?), UNHEX(?), ?)";
    public final static String GET_COMMENT_BY_ID = "select hex(s.id) as id, hex(s.user_id) as user_id, s.text, hex(s.exam_id) as exam_id, s.created_at from comment_exam s, users u where s.id=unhex(?) and u.id=s.user_id";
    public final static String GET_COMMENTS = "select hex(s.id) as id, hex(s.user_id) as user_id, s.text, hex(s.exam_id) as exam_id, s.created_at from comment_exam s, exams e where s.id=unhex(?) created_at < ? and e.id=s.exam_id order by created_at desc limit 25";
    public final static String GET_COMMENTS_AFTER = "select hex(s.id) as id, hex(s.user_id) as user_id, s.text, hex(s.exam_id) as exam_id, s.created_at from comment_exam s, users u where created_at > ? and u.id=s.user_id order by created_a desc limit 25";

}

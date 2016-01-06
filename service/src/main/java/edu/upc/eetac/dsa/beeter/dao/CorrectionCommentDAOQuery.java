package edu.upc.eetac.dsa.beeter.dao;

public class CorrectionCommentDAOQuery
{
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_CORRECTION_COMMENT = "insert into comment_correction (id,  user_id, exam_id, correction_id ,text) values (UNHEX(?), UNHEX(?), UNHEX(?), UNHEX(?), ?)";
    public final static String GET_CORRECTION_COMMENT_BY_ID = "select hex(s.id) as id, hex(s.user_id) as user_id, s.text, hex(s.exam_id) as exam_id,  (SELECT name FROM users WHERE id = s.user_id) as name_creator, hex(s.correction_id) as correction_id ,s.created_at from comment_correction s, users u where s.id=unhex(?) and u.id=s.user_id";
    public final static String GET_CORRECTION_COMMENTS = "select hex(s.id) as id, hex(s.user_id) as user_id, s.text, hex(s.exam_id) as exam_id,  (SELECT name FROM users WHERE id = s.user_id) as name_creator, hex(s.correction_id) as correction_id, s.created_at from comment_correction s where created_at < ? and s.correction_id=unhex(?) order by created_at desc limit 25";
    public final static String GET_CORRECTION_COMMENTS_AFTER = "select hex(s.id) as id, hex(s.user_id) as user_id, s.text, hex(s.exam_id) as exam_id,  (SELECT name FROM users WHERE id = s.user_id) as name_creator, hex(s.correction_id) as correction_id, s.created_at from comment_correction s where created_at > ? and s.correction_id=unhex(?) order by created_at desc limit 25";

}

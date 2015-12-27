package edu.upc.eetac.dsa.beeter.dao;

public class ExamDAOQuery
{
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_EXAM = "insert into exam (id, user_id, subject, text, image) values (UNHEX(?), unhex(?), ?, ?, ?)";
    public final static String GET_EXAM_BY_ID = "select hex(s.id) as id, hex(s.user_id) as user_id, s.text, s.image,  (SELECT count(id) FROM likes WHERE liked_id = s.id AND type = 'exam') as rating, s.created_at, subject from exam s, users u where s.id=unhex(?) and u.id=s.user_id";
    public final static String GET_EXAMS = "select hex(s.id) as id, hex(s.user_id) as user_id, s.text, s.image,  (SELECT count(id) FROM likes WHERE liked_id = s.id AND type = 'exam') as rating, s.created_at, subject from exam s, users u where created_at < ? and u.id=s.user_id order by created_at desc limit 25";
    public final static String GET_EXAMS_AFTER = "select hex(s.id) as id, hex(s.user_id) as user_id, s.text, s.image,  (SELECT count(id) FROM likes WHERE liked_id = s.id AND type = 'exam') as rating, s.created_at, subject from exam s, users u where created_at > ? and u.id=s.user_id order by created_a desc limit 25";
    public final static String UPDATE_EXAM = "update stings set subject=?, content=? where id=unhex(?) ";
    public final static String DELETE_EXAM = "delete from exam where id=unhex(?)";
}

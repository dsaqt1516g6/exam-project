package edu.upc.eetac.dsa.beeter.dao;

public class ExamDAOQuery
{
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_EXAM = "insert into exam (id, userid, subject, text, statement_url) values (UNHEX(?), unhex(?), ?, ?, ?)";
    public final static String GET_EXAM_BY_ID = "select hex(s.id) as id, hex(s.userid) as userid, s.content, s.subject, s.creation_timestamp, s.last_modified, u.fullname from stings s, users u where s.id=unhex(?) and u.id=s.userid";
    public final static String GET_EXAMS = "select hex(s.id) as id, hex(s.userid) as userid, s.subject, s.creation_timestamp, s.last_modified, u.fullname from stings s, users u where creation_timestamp < ? and u.id=s.userid order by creation_timestamp desc limit 25";
    public final static String GET_EXAMS_AFTER = "select hex(s.id) as id, hex(s.userid) as userid, s.subject, s.creation_timestamp, s.last_modified, u.fullname from stings s, users u  where creation_timestamp > ? and u.id=s.userid order by creation_timestamp desc limit 25";
    public final static String UPDATE_EXAM = "update stings set subject=?, content=? where id=unhex(?) ";
    public final static String DELETE_EXAM = "delete from stings where id=unhex(?)";
}

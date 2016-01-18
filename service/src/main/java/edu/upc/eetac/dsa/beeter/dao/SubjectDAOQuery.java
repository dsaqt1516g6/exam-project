package edu.upc.eetac.dsa.beeter.dao;

public class SubjectDAOQuery
{
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_SUBJECT = "insert into subject (id, name) values (UNHEX(?), ?)";
    public final static String GET_SUBJECT_BY_ID = "select hex(s.id) as id, (s.name) as name, s.created_at FROM subject s where s.id=unhex(?)";
    public final static String GET_SUBJECTS = "select hex(s.id) as id, (s.name) as name ,s.created_at FROM subject s where created_at < ? order by created_at desc limit 25";
    public final static String GET_SUBJECTS_AFTER = "select hex(s.id) as id, (s.name) as name ,s.created_at FROM subject s where created_at > ? order by created_at desc limit 25";
}

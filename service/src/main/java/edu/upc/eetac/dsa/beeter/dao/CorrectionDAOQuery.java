package edu.upc.eetac.dsa.beeter.dao;

public class CorrectionDAOQuery
{
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_CORRECTION = "insert into correction (id, user_id, exam_id, text, image_correction) values (UNHEX(?), unhex(?), unhex(?), ?, ?)";
    public final static String GET_CORRECTION_BY_ID = "select hex(s.id) as id, hex(s.user_id) as user_id, hex(s.exam_id) as exam_id, s.text, s.image_correction, (SELECT count(id) FROM likes WHERE liked_id = s.id AND type = 'correction') as rating, (SELECT name FROM users WHERE id = s.user_id) as name_creator, s.created_at from correction s where s.id=unhex(?)";
    public final static String GET_CORRECTIONS = "select hex(s.id) as id, hex(s.user_id) as user_id, hex(s.exam_id) as exam_id, s.text, s.image_correction,  (SELECT count(id) FROM likes WHERE liked_id = s.id AND  type = 'correction') as rating, (SELECT name FROM users WHERE id = s.user_id) as name_creator, s.created_at from correction s where created_at < ? and s.exam_id=unhex(?) order by created_at desc limit 25";
    public final static String GET_CORRECTIONS_AFTER = "select hex(s.id) as id, hex(s.user_id) as user_id, hex(s.exam_id) as exam_id, s.text, s.image_correction, (SELECT count(id) FROM likes WHERE liked_id = s.id AND type = 'correction') as rating, (SELECT name FROM users WHERE id = s.user_id) as name_creator, s.created_at from correction, users u where created_at > ? and u.id=s.user_id order by created_a desc limit 25";
    public final static String UPDATE_CORRECTION = "update correction set subject=?, content=? where id=unhex(?) ";
    public final static String DELETE_CORRECTION = "delete from correction where id=unhex(?)";
}

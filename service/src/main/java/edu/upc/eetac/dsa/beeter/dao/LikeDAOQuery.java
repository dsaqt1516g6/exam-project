package edu.upc.eetac.dsa.beeter.dao;

public class LikeDAOQuery
{
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_LIKE = "insert into likes (id, user_id, liked_id, type) values (UNHEX(?), unhex(?), unhex(?), ?)";
    public final static String GET_LIKE_BY_ID = "select hex(s.id) as id, hex(s.user_id) as user_id, hex(s.liked_id) as liked_id, s.type ,s.created_at from likes s, users u where s.id=unhex(?) and u.id=s.user_id";
    public final static String DELETE_LIKE = "delete from likes where id=unhex(?)";

}

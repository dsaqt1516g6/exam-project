package edu.upc.eetac.dsa.beeter.dao;

/**
 * Created by sergio on 7/09/15.
 */
public interface UserDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_USER = "insert into users (id, name, password) values (UNHEX(?), ?, UNHEX(MD5(?)))";
    public final static String UPDATE_USER = "update users set name=? where id=unhex(?)";
    public final static String ASSIGN_ROLE_REGISTERED = "insert into user_roles (userid, role) values (UNHEX(?), 'registered')";
    public final static String GET_USER_BY_ID = "select hex(u.id) as id, u.name from users u where id=UNHEX(?)";
    public final static String GET_USER_BY_USERNAME = "select hex(u.id) as id, u.name from users u where u.name=?";
    public final static String DELETE_USER = "delete from users where id=unhex(?)";
    public final static String GET_PASSWORD =  "select hex(password) as password from users where id=UNHEX(?)";
    public final static String GET_ROLE_USER_BY_ID =  "select hex(r.userid) as userid, r.role from user_roles r where userid=UNHEX(?)";
}

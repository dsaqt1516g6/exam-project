package edu.upc.eetac.dsa.beeter.dao;

import edu.upc.eetac.dsa.beeter.entity.User;

import java.sql.SQLException;

/**
 * Created by sergio on 7/09/15.
 */
public interface UserDAO {
    public User createUser(String name, String password) throws SQLException, UserAlreadyExistsException;

    public User updateProfile(String id, String name) throws SQLException;

    public User getUserById(String id) throws SQLException;

    public User getUserByname(String name) throws SQLException;

    public boolean deleteUser(String id) throws SQLException;

    public boolean checkPassword(String id, String password) throws SQLException;

    public User getRoleUserById(String role) throws SQLException;
}

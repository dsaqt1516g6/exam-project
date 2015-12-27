package edu.upc.eetac.dsa.beeter.dao;

import edu.upc.eetac.dsa.beeter.db.Database;
import edu.upc.eetac.dsa.beeter.entity.Like;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeDAOImpl implements LikeDAO
{
    @Override
    public Like createLike(String user_id, String liked_id, String type) throws SQLException
    {
        Connection        connection = null;
        PreparedStatement stmt       = null;
        String            id         = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(UserDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            stmt = connection.prepareStatement(LikeDAOQuery.CREATE_LIKE);
            stmt.setString(1, id);
            stmt.setString(2, user_id);
            stmt.setString(3, liked_id);
            stmt.setString(4,type);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return getLikeById(id);
    }

    @Override
    public Like getLikeById(String id) throws SQLException
    {
        Like like = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(LikeDAOQuery.GET_LIKE_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                like = new Like(rs.getString("id"),rs.getString("user_id"), rs.getString("liked_id"), rs.getString("type"), rs.getTimestamp("created_at").getTime() );
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return like;
    }

    @Override
    public boolean deleteLike(String id) throws SQLException
    {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(LikeDAOQuery.DELETE_LIKE);
            stmt.setString(1, id);

            int rows = stmt.executeUpdate();
            return (rows == 1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }


}

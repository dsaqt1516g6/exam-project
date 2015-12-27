package edu.upc.eetac.dsa.beeter.dao;

import edu.upc.eetac.dsa.beeter.entity.Like;

import java.sql.SQLException;

public interface LikeDAO
{
    public Like createLike (String user_id, String liked_id, String type) throws SQLException;
    public Like getLikeById(String id) throws SQLException;
    public boolean deleteLike(String id) throws SQLException;
}

package edu.upc.eetac.dsa.beeter.dao;

import edu.upc.eetac.dsa.beeter.entity.StingCollection;
import edu.upc.eetac.dsa.beeter.entity.Sting;

import java.sql.SQLException;

/**
 * Created by sergio on 9/09/15.
 */
public interface StingDAO {
    public Sting createSting(String userid, String subject, String text) throws SQLException;
    public Sting getStingById(String id) throws SQLException;
    public StingCollection getStings(long timestamp, boolean before) throws SQLException;
    public Sting updateSting(String id, String subject, String content) throws SQLException;
    public boolean deleteSting(String id) throws SQLException;
}

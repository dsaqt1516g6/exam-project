package edu.upc.eetac.dsa.beeter.dao;

import edu.upc.eetac.dsa.beeter.entity.CorrectionComment;
import edu.upc.eetac.dsa.beeter.entity.CorrectionCommentCollection;

import java.sql.SQLException;

public interface CorrectionCommentDAO
{
    public CorrectionComment createCorrectionComment(String user_id, String exam_id, String correction_id,String text) throws SQLException;
    public CorrectionComment getCorrectionCommentById(String id) throws SQLException;
    public CorrectionCommentCollection getComments(long timestamp, boolean before) throws SQLException;
}

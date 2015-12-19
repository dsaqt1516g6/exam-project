package edu.upc.eetac.dsa.beeter.dao;

import edu.upc.eetac.dsa.beeter.entity.Correction;
import edu.upc.eetac.dsa.beeter.entity.CorrectionCollection;

import java.io.InputStream;
import java.sql.SQLException;

public interface CorrectionDAO
{
    public Correction createCorrection(String user_id, String exam_id, String text, InputStream image) throws SQLException;
    public Correction getCorrectionById(String id) throws SQLException;
    public CorrectionCollection getCorrections(long timestamp, boolean before) throws SQLException;
    //public Exam updateExam(String id, String subject, String content) throws SQLException;
    public boolean deleteCorrection(String id) throws SQLException;
}

package edu.upc.eetac.dsa.beeter.dao;

import edu.upc.eetac.dsa.beeter.entity.Exam;

import java.io.InputStream;
import java.sql.SQLException;

public interface ExamDAO
{
    public Exam createExam(String userid, String subject, String text, InputStream image) throws SQLException;
    public Exam getExamById(String id) throws SQLException;
    //public ExamCollection getExams(long timestamp, boolean before) throws SQLException;
    //public Exam updateExam(String id, String subject, String content) throws SQLException;
    //public boolean deleteExam(String id) throws SQLException;
}

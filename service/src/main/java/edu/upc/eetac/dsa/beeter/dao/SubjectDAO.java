package edu.upc.eetac.dsa.beeter.dao;

import edu.upc.eetac.dsa.beeter.entity.Subject;
import edu.upc.eetac.dsa.beeter.entity.SubjectCollection;

import java.sql.SQLException;

public interface SubjectDAO
{
    public Subject createSubject(String name) throws SQLException;
    public Subject getSubjectById(String id) throws SQLException;
    public SubjectCollection getSubjects(long timestamp, boolean before) throws SQLException;
}

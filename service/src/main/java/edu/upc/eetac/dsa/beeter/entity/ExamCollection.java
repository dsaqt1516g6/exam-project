package edu.upc.eetac.dsa.beeter.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExamCollection
{
    private long newestTimestamp;
    private long oldestTimestamp;
    private List<Exam> exams = new ArrayList<>();

    public long getNewestTimestamp()
    {
        return newestTimestamp;
    }

    public void setNewestTimestamp(long newestTimestamp)
    {
        this.newestTimestamp = newestTimestamp;
    }

    public long getOldestTimestamp()
    {
        return oldestTimestamp;
    }

    public void setOldestTimestamp(long oldestTimestamp)
    {
        this.oldestTimestamp = oldestTimestamp;
    }

    public List<Exam> getExams(){return exams;}

    public void setExams(List<Exam> exams){this.exams = exams;}

}

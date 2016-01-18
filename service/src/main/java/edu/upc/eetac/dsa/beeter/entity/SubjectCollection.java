package edu.upc.eetac.dsa.beeter.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubjectCollection
{
    private long newestTimestamp;
    private long oldestTimestamp;
    private List<Subject> subjects = new ArrayList<>();

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

    public List<Subject> getSubjects()
    {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects)
    {
        this.subjects = subjects;
    }
}

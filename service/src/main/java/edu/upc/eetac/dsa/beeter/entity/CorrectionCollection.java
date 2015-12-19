package edu.upc.eetac.dsa.beeter.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CorrectionCollection
{
    private long newestTimestamp;
    private long oldestTimestamp;
    private List<Correction> corrections = new ArrayList<>();

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

    public List<Correction> getCorrections(){return corrections;}

    public void setCorrections(List<Correction> corrections){this.corrections = corrections;}

}

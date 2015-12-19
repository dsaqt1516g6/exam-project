package edu.upc.eetac.dsa.beeter.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CorrectionCommentCollection
{
    private long newestTimestamp;
    private long oldestTimestamp;
    private List<CorrectionComment> correctioncomments = new ArrayList<>();

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

    public List<CorrectionComment> getCorrectioncomments()
    {
        return correctioncomments;
    }

    public void setCorrectioncomments(List<CorrectionComment> correctioncomments)
    {
        this.correctioncomments = correctioncomments;
    }
}

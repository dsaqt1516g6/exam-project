package edu.upc.eetac.dsa.beeter.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CorrectionComment
{
    private String id;
    private String text;
    private String exam_id;
    private String user_id;
    private String correction_id;
    private long created_at;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getExam_id()
    {
        return exam_id;
    }

    public void setExam_id(String exam_id)
    {
        this.exam_id = exam_id;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getCorrection_id()
    {
        return correction_id;
    }

    public void setCorrection_id(String correction_id)
    {
        this.correction_id = correction_id;
    }

    public long getCreated_at()
    {
        return created_at;
    }

    public void setCreated_at(long created_at)
    {
        this.created_at = created_at;
    }
}

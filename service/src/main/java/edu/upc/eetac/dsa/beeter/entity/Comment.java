package edu.upc.eetac.dsa.beeter.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Comment
{
    private String id;
    private String text;
    private String exam_id;
    private String user_id;
    private String creator;
    private long created_at;

    /*public Comment(String id, String text, String exam_id, String user_id)
    {
        this.id = id;
        this.text = text;
        this.exam_id = exam_id;
        this.user_id = user_id;
    }*/
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

    public long getCreated_at()
    {
        return created_at;
    }

    public void setCreated_at(long created_at)
    {
        this.created_at = created_at;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator(String creator)
    {
        this.creator = creator;
    }
}

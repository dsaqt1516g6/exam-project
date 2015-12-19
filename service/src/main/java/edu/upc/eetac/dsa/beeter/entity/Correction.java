package edu.upc.eetac.dsa.beeter.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Correction
{
    private String id;
    private String user_id;
    private String exam_id;
    private String rating;
    private String text;
    private String image_correction;
    private long  created_at;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getExam_id()
    {
        return exam_id;
    }

    public void setExam_id(String exam_id)
    {
        this.exam_id = exam_id;
    }

    public String getRating()
    {
        return rating;
    }

    public void setRating(String rating)
    {
        this.rating = rating;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getImage_correction()
    {
        return image_correction;
    }

    public void setImage_correction(String image_correction)
    {
        this.image_correction = image_correction;
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

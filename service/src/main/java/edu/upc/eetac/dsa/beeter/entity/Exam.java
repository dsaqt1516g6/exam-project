package edu.upc.eetac.dsa.beeter.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Exam
{
    private String id;
    private String userid;
    private String subject;
    private String text;
    private long  created_at;
    private String statement_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public long getCreated_at()
    {
        return created_at;
    }

    public void setCreated_at(long created_at)
    {
        this.created_at = created_at;
    }

    public String getStatement_url()
    {
        return statement_url;
    }

    public void setStatement_url(String statement_url)
    {
        this.statement_url = statement_url;
    }
}

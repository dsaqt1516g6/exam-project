package edu.upc.eetac.dsa.beeter.entity;

import java.util.Date;

public class Like
{
    String id;
    String user_id;
    String liked_id;
    String type;
    private long created_at;


    public Like(String id, String user_id, String liked_id, String type, long created_at)
    {
        this.id = id;
        this.user_id = user_id;
        this.liked_id = liked_id;
        this.type = type;
        this.created_at = created_at;
    }

    public static Like create(String id, String user_id, String liked_id, String type)
    {
        Date date = new Date();
        return new Like(id, user_id, liked_id, type, date.getTime());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


}

package edu.upc.eetac.dsa.beeter.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Correction
{
    private String id;
    private String userid;
    private String subject;
    private String text;
    private String image_correction;
    private long  created_at;
}

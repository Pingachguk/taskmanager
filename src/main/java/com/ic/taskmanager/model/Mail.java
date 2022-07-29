package com.ic.taskmanager.model;

import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeBodyPart;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString.Exclude;

@Data
public class Mail {
    private int id;

    private String subject;

    private String from;

    private Date date;

    private String text;

    @JsonIgnore
    private List<MimeBodyPart> files;
}

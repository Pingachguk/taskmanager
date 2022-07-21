package com.ic.taskmanager.dto;

import java.io.File;
import java.util.Date;

import lombok.Data;

@Data
public class Mail {
    private int id;

    private String subject;

    private String from;

    private Date date;

    private String text;

    private File[] files;
}

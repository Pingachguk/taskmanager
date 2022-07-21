package com.ic.taskmanager.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class EmailRecipient {
    @Email
    private String email;

    @NotEmpty
    private String subject;

    private String text;

    private MultipartFile file;
}

package com.ic.taskmanager.controller;

import com.ic.taskmanager.serializers.EmailRecipient;
import com.ic.taskmanager.services.FileUploadService;
import com.ic.taskmanager.services.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/mail")
public class MailController {
    @Autowired
    private MailService mailService;
    @Autowired
    private FileUploadService fileUploadService;


    @PostMapping(path = "/send", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity sendMailTo(@Valid @ModelAttribute EmailRecipient recipient)
            throws AddressException, MessagingException, IllegalStateException, IOException {
        // this.mailService.sendTo(recipient.getEmail(), recipient.getSubject(),
        // recipient.getText());
        this.fileUploadService.upload(recipient.getFile());
        return ResponseEntity.ok("OK");
    }
}

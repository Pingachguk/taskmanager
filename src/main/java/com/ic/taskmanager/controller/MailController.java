package com.ic.taskmanager.controller;

import com.ic.taskmanager.dto.Mail;
import com.ic.taskmanager.serializers.EmailRecipient;
import com.ic.taskmanager.services.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/mail")
public class MailController {
    @Autowired
    private MailService mailService;

    @RequestMapping(path = "/echo", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<Map<String, String>> echo() {
        return ResponseEntity.ok(Map.of("msg", "Echo"));
    }

    @PostMapping(path = "/outbox/send", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Map<String, String>> sendMailTo(@Valid @ModelAttribute EmailRecipient recipient)
            throws AddressException, MessagingException, IllegalStateException, IOException {
        this.mailService.sendTo(recipient.getEmail(), recipient.getSubject(), recipient.getText());
        return ResponseEntity.ok(Map.of("msg", "Отправлена"));
    }

    @GetMapping(path = "/outbox", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public void getOutboxMails() {

    }

    @GetMapping(path = "/inbox")
    public ResponseEntity<List<Mail>> getInboxMails() throws MessagingException, IOException {
        return ResponseEntity.ok(this.mailService.getInboxMails());
    }

    @GetMapping(path = "/inbox/{mailNum}")
    public ResponseEntity<Mail> getInboxMail(@PathVariable(value = "mailNum") int mailNum)
            throws MessagingException, IOException {
        return ResponseEntity.ok(this.mailService.readMail(mailNum));
    }
}

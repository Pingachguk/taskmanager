package com.ic.taskmanager.controller;

import com.ic.taskmanager.form.EmailRecipient;
import com.ic.taskmanager.model.Mail;
import com.ic.taskmanager.service.MailboxReader;
import com.ic.taskmanager.service.MailboxSender;

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
    private final MailboxSender mailboxSender;
    private final MailboxReader mailboxReader;

    public MailController(MailboxSender mailboxSender, MailboxReader mailboxReader) {
        this.mailboxSender = mailboxSender;
        this.mailboxReader = mailboxReader;
    }

    @RequestMapping(path = "/echo", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<Map<String, String>> echo() {
        return ResponseEntity.ok(Map.of("msg", "Echo"));
    }

    @PostMapping(path = "/outbox/send", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Map<String, String>> sendMailTo(@Valid @ModelAttribute EmailRecipient recipient)
            throws AddressException, MessagingException, IllegalStateException, IOException {
        this.mailboxSender.send(recipient.getEmail(), recipient.getSubject(), recipient.getText());
        return ResponseEntity.ok(Map.of("msg", "Отправлена"));
    }

    @GetMapping(path = "/outbox")
    public ResponseEntity<List<Mail>>  getOutboxMails() throws MessagingException, IOException {
        return ResponseEntity.ok(this.mailboxReader.getMails("Sent"));
    }

    @GetMapping(path = "/inbox")
    public ResponseEntity<List<Mail>> getInboxMails() throws MessagingException, IOException {
        return ResponseEntity.ok(this.mailboxReader.getMails("INBOX"));
    }

    @GetMapping(path = "/inbox/{mailNum}")
    public ResponseEntity<Mail> getInboxMail(@PathVariable(value = "mailNum") int mailNum)
            throws MessagingException, IOException {
        return ResponseEntity.ok(this.mailboxReader.readMail(mailNum));
    }
}

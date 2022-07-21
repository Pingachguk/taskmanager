package com.ic.taskmanager.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ic.taskmanager.model.Mail;

@Service
public class MailService {
    @Autowired
    private MailboxReader mailboxReader;
    @Autowired
    private MailboxSender mailboxSender;

    public void sendTo(String address, String subject, String text) throws AddressException, MessagingException {
        this.mailboxSender.send(address, subject, text);
    }

    public List<Mail> getInboxMails() throws MessagingException, IOException {
        return this.mailboxReader.getMails();
    }

    public Mail readMail(int mailNum) throws MessagingException, IOException {
        return this.mailboxReader.readMail(mailNum);
    }
}

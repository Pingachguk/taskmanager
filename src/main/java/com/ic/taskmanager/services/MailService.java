package com.ic.taskmanager.services;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.stereotype.Service;

import com.ic.taskmanager.models.MailboxSender;

@Service
public class MailService {
    public void sendTo(String address, String subject, String text) throws AddressException, MessagingException {
        MailboxSender mailboxSender = new MailboxSender();
        mailboxSender.send(address, subject, text);
    }

    public void read() {

    }
}

package com.ic.taskmanager.services;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.stereotype.Service;

import com.ic.taskmanager.dto.Mail;
import com.ic.taskmanager.models.MailboxReader;
import com.ic.taskmanager.models.MailboxSender;

@Service
public class MailService {
    public void sendTo(String address, String subject, String text) throws AddressException, MessagingException {
        MailboxSender mailboxSender = new MailboxSender();
        mailboxSender.send(address, subject, text);
    }

    public List<Mail> getInboxMails() throws MessagingException, IOException {
        MailboxReader mailboxReader = new MailboxReader();
        return mailboxReader.getMails();
    }

    public Mail readMail(int mailNum) throws MessagingException, IOException {
        MailboxReader mailboxReader = new MailboxReader();
        return mailboxReader.readMail(mailNum);
    }
}

package com.ic.taskmanager.service;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;

@Service
public class MailboxSender {
    private String protocol = "smtp";

    private int port = 465;

    private String host = "smtp.yandex.ru";

    private String password = "rbgluhpkzftekmlt";

    private String email = "work.account.test@yandex.ru";

    private Properties props;

    private Session session;

    public MailboxSender() {
        this.props = new Properties();
        this.props.put("mail.smtp.host", this.host);
        this.props.put("mail.smtp.auth", "true");
        this.props.put("mail.smtp.socketFactory.port", this.port);
        this.props.put("mail.smtp.ssl.enable", "true");
    }

    public void send(String address, String subject, String text) throws AddressException, MessagingException {
        this.session = this.getSession();

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(this.email));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(address));
        message.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(text, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }

    private Session getSession() {
        return Session.getDefaultInstance(this.props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
    }
}

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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;

@Service
public class MailboxSender {
    @Value("${tm.smtp.host}")
    private String host;

    @Value("${tm.smtp.port}")
    private int port;

    @Value("${tm.operator.password}")
    private String password;

    @Value("${tm.operator.email}")
    private String email;

    private final Properties props;

    public MailboxSender() {
        this.props = new Properties();
        this.props.put("mail.smtp.host", this.host);
        this.props.put("mail.smtp.auth", "true");
        this.props.put("mail.smtp.socketFactory.port", this.port);
        this.props.put("mail.smtp.ssl.enable", "true");
    }

    public void send(String address, String subject, String text) throws AddressException, MessagingException {
        Session session = this.getSession();

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

package com.ic.taskmanager.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Store;

import com.ic.taskmanager.model.Mail;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

public class MailboxReader {
    protected String protocol = "pop3";

    protected int port = 995;

    private String host = "pop.yandex.ru";

    private String password = "rbgluhpkzftekmlt";

    private String email = "work.account.test@yandex.ru";

    private String folderName;

    private Properties props;

    private Session session;

    private Store store;

    public MailboxReader() throws MessagingException {
        this.props = new Properties();
        this.props.put(String.format("mail.%s.port", this.protocol), this.port);
        this.props.put(String.format("mail.%s.host", this.protocol), this.host);
        this.props.put(String.format("mail.%s.user", this.protocol), this.email);
        this.props.put(String.format("mail.%s.socketFactory.class", this.protocol), "javax.net.ssl.SSLSocketFactory");
        this.props.put(String.format("mail.%s.socketFactory.fallback", this.protocol), "false");
        this.props.put(String.format("mail.%s.socketFactory.port", this.protocol), "995");

        this.session = Session.getDefaultInstance(this.props);

        this.store = this.session.getStore(this.protocol);
        this.store.connect(this.host, this.port, this.email, this.password);
    }

    public List<Mail> getMails() throws MessagingException, IOException {
        Folder inbox = store.getFolder(this.folderName);
        inbox.open(Folder.READ_ONLY);
        Message[] messages = inbox.getMessages();

        List<Mail> mails = new ArrayList<>();
        for (Message message : messages) {
            Mail mail = new Mail();
            mail.setFrom(message.getFrom()[0].toString());
            mail.setSubject(message.getSubject());
            mail.setDate(message.getSentDate());
            mail.setId(message.getMessageNumber());

            mails.add(mail);
        }

        return mails;
    }

    public Mail readMail(int mailNum) throws MessagingException, IOException {
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);
        Message message = inbox.getMessage(mailNum);

        Mail mail = new Mail();
        mail.setId(message.getMessageNumber());
        mail.setFrom(message.getFrom()[0].toString());
        mail.setSubject(message.getSubject());
        mail.setDate(message.getSentDate());
        mail.setText(message.getContent().toString());

        return mail;
    }
}

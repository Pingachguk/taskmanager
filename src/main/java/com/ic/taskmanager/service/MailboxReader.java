package com.ic.taskmanager.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ic.taskmanager.interfaces.UploadService;
import com.ic.taskmanager.model.Mail;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;

@Service
public class MailboxReader {
    @Autowired
    private UploadService uploadService;

    private String protocol = "imap";

    private int port = 993;

    private String host = "imap.yandex.ru";

    private String password = "rbgluhpkzftekmlt";

    private String email = "work.account.test@yandex.ru";

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
        this.props.put(String.format("mail.%s.socketFactory.port", this.protocol), this.port);

        this.session = Session.getDefaultInstance(this.props);

        this.store = this.session.getStore(this.protocol);
    }

    public List<Mail> getMails(String folderName) throws MessagingException, IOException {
        this.store.connect(this.host, this.port, this.email, this.password);

        Folder inbox = store.getFolder(folderName);
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

        this.store.close();
        return mails;
    }

    public Mail readMail(int mailNum) throws MessagingException, IOException {
        this.store.connect(this.host, this.port, this.email, this.password);

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);
        Message message = inbox.getMessage(mailNum);

        Mail mail = new Mail();
        mail.setId(message.getMessageNumber());
        mail.setFrom(message.getFrom()[0].toString());
        mail.setSubject(message.getSubject());
        mail.setDate(message.getSentDate());

        if (message.isMimeType("text/*")) {
            mail.setText(message.getContent().toString());
        }

        if (message.isMimeType("multipart/mixed")) {
            this.readMultipart(mail, message);
        }

        this.store.close();
        return mail;
    }

    private void readMultipart(Mail mail, Message message) throws IOException, MessagingException {
        Multipart multipart = (Multipart) message.getContent();
        int countParts = multipart.getCount();
        for (int i = 0; i < countParts; i++) {
            MimeBodyPart part = (MimeBodyPart) multipart.getBodyPart(i);

            if (part.isMimeType("text/*")) {
                mail.setText(part.getContent().toString());
            }

            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                String fileName = part.getFileName();
                String saveFileName = MimeUtility.decodeText(fileName);
                part.saveFile("uploads/"+saveFileName);
            }
        }
    }
}
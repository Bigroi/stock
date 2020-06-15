package com.bigroi.stock.messager.email;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import com.bigroi.stock.bean.db.Email;
import com.bigroi.stock.dao.EmailDao;
import com.bigroi.stock.docs.DocumentGenerator;
import com.bigroi.stock.messager.generator.Generator;

import javax.activation.DataHandler;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class SesEmailClient implements EmailClient {

    private final Generator generator;
    private final String defaultFrom;
    private final String support;
    private final AmazonSimpleEmailService innerClient;
    private final EmailDao emailDao;
    private final DocumentGenerator documentGenerator;

    public SesEmailClient(
            AmazonSimpleEmailService innerClient,
            Generator generator,
            String defaultFrom,
            String support,
            EmailDao emailDao,
            DocumentGenerator documentGenerator
    ) {
        this.innerClient = innerClient;
        this.generator = generator;
        this.defaultFrom = defaultFrom;
        this.support = support;
        this.emailDao = emailDao;
        this.documentGenerator = documentGenerator;
    }

    @Override
    public void sendMessage(
            Locale locale,
            String emailsTo,
            EmailType emailType,
            Map<String, Object> messageParams,
            String attachmentFileName,
            Map<String, Object> attachmentParams
    ) {
        var text = generator.generateBasedOnTemplateFile(
                emailType.getEmailTemplateName() + "-" + locale + ".ftl",
                messageParams
        );

        var email = new Email();
        email.setBody(text.substring(text.indexOf('\n') + 1));
        email.setRecipient(emailsTo);
        email.setSubject(text.substring(0, text.indexOf('\n')));

        if (attachmentFileName != null && attachmentParams != null) {
            var fullFileName = attachmentFileName + "-" + locale + ".doc";
            email.setFileName(fullFileName);
            email.setFile(documentGenerator.generate(fullFileName, attachmentParams));
        }

        emailDao.add(email);
    }

    @Override
    public void sendMessageNow(Email email) {
        try {
            var session = Session.getDefaultInstance(new Properties());
            var message = new MimeMessage(session);

            message.setSubject(email.getSubject(), "UTF-8");
            message.setFrom(new InternetAddress(defaultFrom));
            message.setRecipients(MimeMessage.RecipientType.TO, email.getRecipient());

            var msgBody = new MimeMultipart("alternative");
            var htmlPart = new MimeBodyPart();
            htmlPart.setContent(email.getBody(), "text/html; charset=UTF-8");
            msgBody.addBodyPart(htmlPart);

            var wrap = new MimeBodyPart();
            wrap.setContent(msgBody);

            var msg = new MimeMultipart();
            message.setContent(msg);
            msg.addBodyPart(wrap);

            if (email.getFileName() != null) {
                var att = new MimeBodyPart();
                var fds = new ByteArrayDataSource(email.getFile(), "application/octet-stream");
                att.setDataHandler(new DataHandler(fds));
                att.setFileName(email.getFileName());
                msg.addBodyPart(att);
            }

            var outputStream = new ByteArrayOutputStream();
            message.writeTo(outputStream);
            var rawMessage = new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()));

            var rawEmailRequest = new SendRawEmailRequest(rawMessage);

            innerClient.sendRawEmail(rawEmailRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getDefaultFrom() {
        return defaultFrom;
    }

    public String getSupport() {
        return support;
    }
}

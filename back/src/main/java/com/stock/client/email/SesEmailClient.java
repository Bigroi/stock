package com.stock.client.email;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import com.stock.entity.Language;
import com.stock.generator.DocumentGenerator;
import com.stock.generator.Generator;

import javax.activation.DataHandler;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Properties;

public class SesEmailClient implements EmailClient {

    private final Generator generator;
    private final String defaultFrom;
    private final String support;
    private final AmazonSimpleEmailService innerClient;
    private final DocumentGenerator documentGenerator;

    public SesEmailClient(
            AmazonSimpleEmailService innerClient,
            Generator generator,
            String defaultFrom,
            String support,
            DocumentGenerator documentGenerator
    ) {
        this.innerClient = innerClient;
        this.generator = generator;
        this.defaultFrom = defaultFrom;
        this.support = support;
        this.documentGenerator = documentGenerator;
    }

    @Override
    public void sendMessage(
            Language language,
            String emailsTo,
            EmailType emailType,
            Map<String, Object> messageParams,
            String attachmentFileName,
            Map<String, Object> attachmentParams
    ) {
        var text = generator.generateBasedOnTemplateFile(
                emailType.getEmailTemplateName() + "-" + language + ".ftl",
                messageParams
        );

        try {
            var session = Session.getDefaultInstance(new Properties());
            var message = new MimeMessage(session);

            message.setSubject(text.substring(0, text.indexOf('\n')), "UTF-8");
            message.setFrom(new InternetAddress(defaultFrom));
            message.setRecipients(MimeMessage.RecipientType.TO, emailsTo);

            var msgBody = new MimeMultipart("alternative");
            var htmlPart = new MimeBodyPart();
            htmlPart.setContent(text.substring(text.indexOf('\n') + 1), "text/html; charset=UTF-8");
            msgBody.addBodyPart(htmlPart);

            var wrap = new MimeBodyPart();
            wrap.setContent(msgBody);

            var msg = new MimeMultipart();
            message.setContent(msg);
            msg.addBodyPart(wrap);

            if (attachmentFileName != null && attachmentParams != null) {
                var fullFileName = attachmentFileName + "-" + language + ".doc";
                var att = new MimeBodyPart();
                var fds = new ByteArrayDataSource(
                        documentGenerator.generate(fullFileName, attachmentParams),
                        "application/octet-stream"
                );
                att.setDataHandler(new DataHandler(fds));
                att.setFileName(fullFileName);
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

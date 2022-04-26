package org.esprit.services;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class MailJava {

    public static  void sendMail(String object,String body) throws MessagingException {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        //prop.put("mail.smtp.host", "smtp.mailtrap.io");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("utravelpidev", "Utravel2022");
            }
        });
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("utravelpidev@gmail.com"));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse("anis.tounekti@esprit.tn"));
        message.setSubject(object);



        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(body, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }
}

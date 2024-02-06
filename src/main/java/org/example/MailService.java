package forReport;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

import static forReport.MailConstants.EMAIL_SENDER;
import static forReport.MailConstants.EMAIL_SENDER_PASSWORD;

public class MailService {
    private Session session;

    public static void main(String[] args) {
        MailService mailService = new MailService();
        mailService.sendEmail("silvia.atanasova@softwareag.com", "Subject", "Message Text");
    }

    public MailService() {
        Properties props = new Properties();

        props.put("mail.smtp.host", "daesmtp.eur.ad.sag");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "daesmtp.eur.ad.sag");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_SENDER, EMAIL_SENDER_PASSWORD);
            }
        };
        session = Session.getInstance(props, auth);
    }

    public void sendEmail(String recipient, String subject, String messageText) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.addHeader("Content-type", "text/HTML; charset=UTF-8");
            message.addHeader("format", "flowed");
            message.addHeader("Content-Transfer-Encoding", "8bit");

            message.setFrom(new InternetAddress(EMAIL_SENDER));
            message.setReplyTo(InternetAddress.parse("address@mail.com")); // no-reply address or other specified
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient)); // comma separated sequence of addresses into objects
            message.setSubject(subject);
            message.setSentDate(new Date()); // current

            Transport.send(message);

        } catch (MessagingException e) {
            System.out.println("Failed to send email");
            throw new RuntimeException(e);
        }

    }

}

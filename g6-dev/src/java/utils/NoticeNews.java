/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import static constants.LmsConstants.Credentials.AUTHOR_EMAIL;
import static constants.LmsConstants.Credentials.GMAIL_PASSWORD;
import static constants.LmsConstants.Credentials.GMAIL_USERNAME;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Admin
 */
public class NoticeNews {

    public void sendEmail(List<String> gmail, String subject, String body) {
        try {
            final String fromEmail = GMAIL_USERNAME;
            final String password = GMAIL_PASSWORD;
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            };
            Session session = Session.getInstance(props, auth);
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress(fromEmail, AUTHOR_EMAIL));
            msg.setReplyTo(InternetAddress.parse(fromEmail, false));
            msg.setSubject(subject, "UTF-8");
            msg.setContent(body, "text/html; charset=UTF-8");
            msg.setSentDate(new Date());
            List<InternetAddress> ia = new ArrayList<>();
            for (String mail : gmail) {
                ia.add(new InternetAddress(mail));
            }
            InternetAddress[] recipients = ia.toArray(new InternetAddress[gmail.size()]);
            msg.setRecipients(Message.RecipientType.TO, recipients);
            Transport.send(msg);
            System.out.println("Gui mail thanh cong");
        } catch (Exception ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
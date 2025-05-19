//package com.ceer.email;
//
//import java.util.Properties;
//import javax.mail.*;
//import javax.mail.Authenticator;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//
//
//
//
//public class GEmailSender {
//	public static  boolean sendEmail(String to, String from, String subject, String text) {
//		boolean flag=false;
//		//logic 
//		//smtp properties
//		Properties properties=new Properties();
//		properties.put("mail.smtp.auth", true);
//		properties.put("mail.smtp.starttls.enable", true);
//		properties.put("mail.smtp.port", "587");
//		//properties.put("mail.smtp.host", "smtp.gmail.com");
//		properties.put("mail.smtp.ssl.protocols", "TLSv1");
//		properties.put("mail.smtp.host","smtp.ceermotors.com");
//		String username="arhmarehman";
//		String password="zixdlpfbtgiojtbm";
//		
//		//session
//		 Session session = Session.getInstance(properties, new Authenticator() {
//	            @Override
//	            protected PasswordAuthentication getPasswordAuthentication() {
//	                // Return the username and password for Gmail SMTP authentication
//	                return new PasswordAuthentication(username, password);
//	            }
//	        });
//		 try {
//			 Message message=new MimeMessage(session);
//			 message.setFrom(new InternetAddress(from));
//			 message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
//			 message.setSubject(subject);
//			 message.setText(text);
//			 Transport.send(message);
//			 flag=true;
//					 }catch(Exception e) {
//			 e.printStackTrace();
//		 }
//		 
//		return flag;
//		 
//		
//		
//		
//	}
//
//}
//
//

package com.ceer.email;

import java.io.File;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class GEmailSender {

    public static boolean sendEmailWithAttachment(String to, String from, String subject, String text, File attachment) {
        boolean flag = false;

        // SMTP properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.host", "smtp.gmail.com");

        // SMTP authentication
        String username = "";//add your from email but exclude the part from @
        String password = "zixdlpfbtgiojtbm"; // Use the app password which you can create for gmail(you have to create an app password, don't add your real password.)

        // Create a session
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);

            // Create a multipart message (text + attachment)
            Multipart multipart = new MimeMultipart();

            // Add email text content
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(text);
            multipart.addBodyPart(textPart);

            // Add attachment
            if (attachment != null && attachment.exists()) {
                MimeBodyPart attachmentPart = new MimeBodyPart();
                DataSource source = new FileDataSource(attachment);
                attachmentPart.setDataHandler(new DataHandler(source));
                attachmentPart.setFileName(attachment.getName());
                multipart.addBodyPart(attachmentPart);
            } else {
                System.out.println("Attachment not found: " + attachment.getAbsolutePath());
            }

            // Set content and send email
            message.setContent(multipart);
            Transport.send(message);
            flag = true;
            System.out.println("Email sent successfully with attachment.");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send email.");
        }

        return flag;
    }
}


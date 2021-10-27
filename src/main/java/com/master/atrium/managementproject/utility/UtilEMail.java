package com.master.atrium.managementproject.utility;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;

import org.springframework.stereotype.Component;

@Component
public class UtilEMail extends HttpServlet {
	
	private static final long serialVersionUID = 6098477709328292413L;

	public void sendEmail(List<String> toMailList, String mailSubject, String mailText) {
		
		String mailFrom = "projectmanagementnoreply0@gmail.com";
		
		Properties props = getProperties();

		Session session = Session.getDefaultInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				String username = mailFrom;
				String password = "masteruah";
				return new PasswordAuthentication(username, password);
			}
		});

		MimeMessage msg = new MimeMessage(session);

		try {
			System.out.println("> Try block");
			msg.setFrom(new InternetAddress(mailFrom));
//			msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(mailTo));
			msg.setSubject(mailSubject);
			msg.setText(mailText);
			System.out.println("> Transport created");
			Transport transport = session.getTransport("smtp");
			for(String mailTo : toMailList) {
				msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(mailTo));
				System.out.println("> Sending E-mail...");
				Transport.send(msg);
			}
			System.out.println("E-mail sent!");
			transport.close();
			System.out.println("> Closed connection");
		} catch (Exception exc) {
			System.out.println("> Catch block");
			System.out.println(exc);
			exc.printStackTrace();
		}

		System.out.println("EMAIL enviado...");

	}

	public Properties getProperties() {
		Properties props = new Properties();

		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.stmp.user", "username of the sender");
		/* If you want you use TLS
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.password", "password of the sender");
		If you want to use SSL*/
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.ssl.checkserveridentity", true);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		return props;
	}
}

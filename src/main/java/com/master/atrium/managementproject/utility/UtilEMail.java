package com.master.atrium.managementproject.utility;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Clase de utilidades para el envío de correo electrónico
 * @author Rodrigo
 *
 */
@Component
public class UtilEMail extends HttpServlet {
	/** Log de la clase */
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 6098477709328292413L;
	
	/**
	 * Constante de correo de la aplicación
	 */
	private static final String MAIL_FROM = "projectmanagementnoreply0@gmail.com";
	private static final String PASSWORD = "masteruah";
	/**
	 * Envía un correo electrónico	
	 * @param toMailList
	 * @param mailSubject
	 * @param mailText
	 */
	public void sendEmail(List<String> toMailList, String mailSubject, String mailText) {		
		Properties props = getProperties();
		Session session = Session.getDefaultInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {				
				return new PasswordAuthentication(MAIL_FROM, PASSWORD);
			}
		});

		MimeMessage msg = new MimeMessage(session);

		try {
			LOG.info("> Try block");
			msg.setFrom(new InternetAddress(MAIL_FROM));
			msg.setSubject(mailSubject);
			msg.setText(mailText);
			LOG.info("> Transport created");
			Transport transport = session.getTransport("smtp");
			for(String mailTo : toMailList) {
				msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(mailTo));
				LOG.info("> Sending E-mail...");
				Transport.send(msg);
			}
			LOG.info("E-mail sent!");
			transport.close();
			LOG.info("> Closed connection");
		} catch (Exception exc) {
			LOG.info(String.join("", "> Catch block", exc.getMessage()));
		}

		LOG.info("EMAIL enviado...");

	}

	/**
	 * Obtiene las propiedades para el envío del correo electrónico
	 * @return
	 */
	public Properties getProperties() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.stmp.user", "username of the sender");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.ssl.checkserveridentity", true);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		return props;
	}
}

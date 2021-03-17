package com.seller.quickbuy.QuickBuyApp.serviceImpl;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.seller.quickbuy.QuickBuyApp.service.EmailService;
import com.seller.quickbuy.QuickBuyApp.utils.QuickBuyUtils;

@Service
public class EmailServiceImpl implements EmailService {

	
	private static final Logger logger = LogManager.getLogger(EmailServiceImpl.class);
	
	private String from = QuickBuyUtils.getPropertyValue("mail.from");
	private String host = QuickBuyUtils.getPropertyValue("mail.host");
	private String port = QuickBuyUtils.getPropertyValue("mail.port");
	//private String cc = QuickBuyUtils.getPropertyValue("mail.cc");
	private String password = QuickBuyUtils.getPropertyValue("mail.password");
	
	Properties properties = System.getProperties();
	
	Session session = Session.getInstance(properties,new javax.mail.Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(from,password);
		}
	});
	
	@Override
	public void sendEmail(String firstName, String email, String attachFile) {
		logger.info("In EmailServiceImpl sendEmail().......START");
		
		String message = "Dear <b>"+firstName+"</b>,<br>"
				+ "<p>Thank you for Shopping with us. We confirmed that your order has shipped."
				+ "Order and payment details of your transaction can be found on the invoice."
				+ "<br>We hope to see you again soon!" 
				+"<br><a href='http://140.238.229.244:8080/quickbuy-ang/'>U@QuickBuy.in</a></p>";
		
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");
		session.setDebug(true);
		
		MimeMessage mimeMessage = new MimeMessage(session);
		try {
			mimeMessage.setFrom(new InternetAddress(from));
			mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(email));
			//mimeMessage.addRecipient(Message.RecipientType.CC,new InternetAddress(cc));
			mimeMessage.setSubject("Order Confirmation");
			mimeMessage.setSentDate(new Date());
			
			//create message part
			MimeBodyPart msgBodyPart = new MimeBodyPart();
			msgBodyPart.setContent(message,"text/html");
			
			//creates multi-part
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(msgBodyPart);
			
			//add attachment
			if(attachFile != null && attachFile.length() > 0)
			{
				MimeBodyPart attachPart = new MimeBodyPart();
				attachPart.attachFile(attachFile);
				multipart.addBodyPart(attachPart);
			}
			mimeMessage.setContent(multipart);
			//Transport.send(mimeMessage);
			Transport.send(mimeMessage, mimeMessage.getAllRecipients());
			logger.info("Message Sent....");
		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			logger.info("Message Fail....");
			e.printStackTrace();
		}
		logger.info("In EmailServiceImpl sendEmail().......END");
	}

}

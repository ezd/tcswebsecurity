package com.example.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.example.entities.User;
@Service
public class CustomMailSender {

	@Autowired
	private MailSender mailSender;
	private SimpleMailMessage msgTemplate;
//	private SimpleMailMessage templateMessage;

	public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

//	public SimpleMailMessage getTemplateMessage() {
//		return templateMessage;
//	}
//
//	public void setTemplateMessage(SimpleMailMessage templateMessage) {
//		this.templateMessage = templateMessage;
//	}
	

	public boolean sendEmail(User user, String subject, String message,String fromEmail) {
		this.msgTemplate=new SimpleMailMessage();
		this.msgTemplate.setFrom(fromEmail);
		this.msgTemplate.setTo(user.getUsername());
		this.msgTemplate.setSubject(subject);
		this.msgTemplate.setText(message);
		SimpleMailMessage msg=new SimpleMailMessage(msgTemplate);
		mailSender.send(msg);
		try {
			
			this.mailSender.send(msg);
			return true;
		} catch (Exception e) {
			System.out.println("Exception is:" + e.getStackTrace());
		}
		return false;
	}

}

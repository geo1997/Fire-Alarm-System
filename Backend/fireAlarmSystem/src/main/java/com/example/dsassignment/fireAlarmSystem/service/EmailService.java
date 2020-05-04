package com.example.dsassignment.fireAlarmSystem.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.example.dsassignment.fireAlarmSystem.model.MailRequest;
import com.example.dsassignment.fireAlarmSystem.model.MailResponse;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Configuration;


@Service //initialized as a service class
public class EmailService {

	@Autowired //spring injects JavaMailSender when the EmailService is created
	private JavaMailSender sender;
	
	@Autowired //spring injects Configuration when the EmailService is created
	private Configuration config;
	
	
	//method implementation to send the eamil 
	public MailResponse sendEmail(MailRequest request, Map<String, Object> model) {
		MailResponse response = new MailResponse(); //initilaize a reference to the MailResponse class
		MimeMessage message = sender.createMimeMessage(); //creates a new JavaMail mime message.
		try {
			// set mediaType
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			
			
			// reference to the template to be used in the eamil
			Template t = config.getTemplate("email-template.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

			//set the values
			helper.setTo(request.getTo());
			helper.setText(html, true);
			helper.setSubject(request.getSubject());
			helper.setFrom(request.getFrom());
			sender.send(message);

			response.setMessage("mail send to : " + request.getTo());
			response.setStatus(Boolean.TRUE);

		} catch (MessagingException | IOException | TemplateException e) {
			response.setMessage("Mail Sending failure : "+e.getMessage());
			response.setStatus(Boolean.FALSE);
		}

		return response;
	}
}

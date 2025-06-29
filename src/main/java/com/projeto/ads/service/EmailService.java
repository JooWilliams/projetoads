package com.projeto.ads.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendEmail(String remetente,String destino,String assunto,String corpo) {
		SimpleMailMessage obj= new SimpleMailMessage();
		obj.setFrom(remetente);
		obj.setTo(destino);
		obj.setText(corpo);
		obj.setSubject(assunto);
		javaMailSender.send(obj);
		
	}
	

}

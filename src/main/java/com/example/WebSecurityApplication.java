package com.example;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.example.entities.Role;
import com.example.entities.User;
import com.example.entities.UserToken;
import com.example.enums.RoleEnum;
import com.example.repository.RoleRepo;
import com.example.repository.UserRepository;
import com.example.services.UserRegistrationService;
import com.example.services.UserTokenService;

@SpringBootApplication
public class WebSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSecurityApplication.class, args);
	}

	@Value("${webmaster.email}")
	String webmasterEmail;
	
	@Value("${webmaster.password}")
	String webmasterpassword;
	
	@Autowired
	UserRegistrationService userRegistrationService;
	
	@Value("${token.expiration.inmin}")
	int expirationMiniut;
	
	@Autowired
	UserTokenService userTokenService;
	
	
	
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

	public SimpleMailMessage getMsgTemplate() {
		return msgTemplate;
	}

	public void setMsgTemplate(SimpleMailMessage msgTemplate) {
		this.msgTemplate = msgTemplate;
	}
	
	/*@PostConstruct
	public void dbinit(){
		Role adminRole=new Role(RoleEnum.ADMIN);
		
		User adminUser=new User("biliyala.ezd2@gmail.com", webmasterpassword, "Admin", "AdminLast", "lasColinas", "75039", "Irving", "USA", "12-23-1982", "206-229-1976",true);
		User userUser=new User("user@gmail.com", "123456", "kebede", "Tesema", "lasColinas", "75039", "Irving", "USA", "12-23-1982", "206-229-1976",true);
//		Set<Role> roles=new HashSet<>();
//		roles.add(adminRole);
//		adminUser.getRoles().add(adminRole);
//		adminRole.getUsers().add(adminUser);
		userRegistrationService.saveRole(adminRole);
		adminUser.setRole(adminRole);
		User savedUser=userRegistrationService.saveUserInfo(adminUser);

		Role userRole=new Role(RoleEnum.USER);
		userUser.setRole(userRole);
		userRegistrationService.saveRole(userRole);
		
		User savedUser2=userRegistrationService.saveUserInfo(userUser);
		//UserToken ut= userTokenService.createToken(savedUser, expirationMiniut);
		
		
//		this.msgTemplate=new SimpleMailMessage();
//		this.msgTemplate.setFrom(webmasterEmail);
//		this.msgTemplate.setTo("biliyala.ezd2@gmail.com");
//		this.msgTemplate.setSubject("Spring boot mail sender test");
//		this.msgTemplate.setText("It should works");
//		SimpleMailMessage msg=new SimpleMailMessage(msgTemplate);
//		mailSender.send(msg);
	}*/
}

package com.example.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entities.User;
import com.example.entities.UserToken;
import com.example.services.UserRegistrationService;
import com.example.services.UserTokenService;
import com.example.utils.CustomMailSender;
@Controller
public class LoginController {
	
	@Autowired
	UserRegistrationService userRegistrationService;
	
	@Autowired
	UserTokenService userTokenService;
	@Autowired
	CustomMailSender customMailSender;
	
	@Value("${webmaster.email}")
	String webmasterEmail;
	
	@Value("${token.expiration.inmin}")
	int expirationMiniut;
	
	@RequestMapping("/login")
	public String getLogin(){
		return "login";
	}
	@RequestMapping(value="/forgetPassword", method=RequestMethod.GET)
	public String forgetPassword(Model model,User user){
		System.out.println("come to the get");
		return "emailForm";
	}
	
	@RequestMapping(value="/updatePassword",method=RequestMethod.GET)
	public String updatePassword(Model model,@RequestParam("id") Long id,@RequestParam("token") String token){
		boolean isValidUser=userRegistrationService.isValidUser(id);
		if(!isValidUser){
			model.addAttribute("urlmsg","Unregisterd User.");
		}else if(userTokenService.isTokenNotExist(id,token)){
			model.addAttribute("urlmsg","Unknown token");
		}else if(userTokenService.isTokenExpired(id,token)){
			model.addAttribute("urlmsg", "The token has expried");
		}else{
			model.addAttribute("urlmsg", "success");
			model.addAttribute("userId", id);
		}
		
		return "resetPassword";		
	}
	@RequestMapping(value="/updatePassword",method=RequestMethod.POST)
	public String updatePasswordPost(Model model,@RequestParam("userId") Long userId,@RequestParam("password") String newPassword){
		System.out.println("the new password is:"+newPassword);
		if(userRegistrationService.updateUserPassword(userId,newPassword)){
			model.addAttribute("updatemsg", "success");
			System.out.println("success");
		}else{
			System.out.println("fail");
			model.addAttribute("updatemsg", "fail");
		}
		model.addAttribute("urlmsg", "success");
		return "resetPassword";		
	}
	
	@RequestMapping(value="/forgetPassword", method=RequestMethod.POST)
	public String forgetPasswordSave(HttpServletRequest request,@RequestParam("email") String userEmail,Model model){
		User user=userRegistrationService.getUserByEmail(userEmail);
		if(null!=user){
			UserToken ut=userTokenService.createToken(user, expirationMiniut);
			String url=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/updatePassword?id="+user.getId()+"&&token="+ut.getToken();
			String message="Please follow the following link to reset your password.\n"+url+"\n Regards \n Siraflega";
			if (customMailSender.sendEmail(user, "Forget password reset", message,webmasterEmail)){
				System.out.println("email successfuly send");
			};
		}else{
			System.out.println("dont find the email used in the system");
		}
		model.addAttribute("emailSent", "true");
		return "emailForm";
	}
	
	@RequestMapping({"/","publicinfo"})
	public String getIndex(){
		return "index";
	}
	
	@RequestMapping("/accessdenide")
	public String getDenied(){
		return "accessdenidePage";
	}

	@RequestMapping("/contactus")
	public String getContacus(){
		return "contactus";
	}
	
	@RequestMapping("/aboutus")
	public String getAboutus(){
		return "aboutus";
	}
}

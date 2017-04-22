package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.User;
import com.example.services.UserRegistrationService;


@RestController
public class UserRegistrationRestController {
	@Autowired
	UserRegistrationService userRegistrationService;
	
//	@RequestMapping(value="/saveUser",method=RequestMethod.POST)
//	public @ResponseBody User saveUser(@RequestBody User userinfo){
////		UserInfo userinfo=new UserInfo("Ezedin", "Asseffa", "valley ranch", "75063", "Irving", "USA", "2062291976", "biliyala.ezd2@gmail.com", "password");
//		User saveUserInfo = userRegistrationService.saveUserInfo(userinfo,null);
//		return saveUserInfo;
//	}
	
//	@RequestMapping(value="/updateUser",method=RequestMethod.PUT)
//	public @ResponseBody User updateUser(@RequestBody User userinfo){
////		UserInfo userinfo=new UserInfo("Ezedin", "Asseffa", "valley ranch", "75063", "Irving", "USA", "2062291976", "biliyala.ezd2@gmail.com", "password");
//		User userInfoByEmail = userRegistrationService.getUserInfoByEmail(userinfo.getEmail());
//		if(null!=userInfoByEmail){
//			userInfoByEmail.setStreet(userinfo.getStreet());
//			userInfoByEmail.setCity(userinfo.getCity());
//			userInfoByEmail.setCountry(userinfo.getCountry());
//			userInfoByEmail.setEmail(userinfo.getEmail());
//			userInfoByEmail.setFirstName(userinfo.getFirstName());
//			userInfoByEmail.setLastName(userinfo.getLastName());
//			userInfoByEmail.setPhone(userinfo.getPhone());
//			userInfoByEmail.setUnEncryptedPassword(userinfo.getUnEncryptedPassword());
//			userInfoByEmail.setZipCode(userinfo.getZipCode());
//			System.out.println("the id is:"+userInfoByEmail.getId());
//			
//		}
//		User saveUserInfo = userRegistrationService.updateUserInfo(userInfoByEmail);
//		return saveUserInfo;
//	}
	
//	@RequestMapping(value="/isValidUser",method=RequestMethod.POST)
//	public @ResponseBody boolean isValidUser(@RequestBody User userinfo){
//			if(userRegistrationService.isValidUser(userinfo.getEmail(),userinfo.getUnEncryptedPassword())){
//				return true;
//			}else{
//				return false;
//			}
//	}
	
//	@RequestMapping(value="/isEmailAvailable",method=RequestMethod.GET)
//	public @ResponseBody boolean isEmailAvailable(@RequestParam("email") String email){
//		User userbyemailis=userRegistrationService.getUserInfoByEmail(email);
//		if(userbyemailis!=null){
//			
//			return true;
//		}else{
//			return false;
//		}
//	}
	
	@RequestMapping(value="/getUser/{userId}",method=RequestMethod.GET)
	public @ResponseBody User getUser(@PathVariable("userId") Long userId){
		User userInfoById = userRegistrationService.getUserInfo(userId);
		return userInfoById;
	}
	
	@RequestMapping(value="/deleteUser/{userId}",method=RequestMethod.GET)
	public @ResponseBody String deleteUser(@PathVariable("userId") Long userId){
		try {
			userRegistrationService.deletUserInfo(userId);
			return "successfully deleted";
		} catch (Exception e) {
			// TODO: handle exception
			return "not able deleted";
		}
		
		
	}

}

package com.example.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.converter.LocalDateTimeAttributeConverter;

@Entity
public class UserToken {
	
	public UserToken() {
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(unique=true)
	private String token;
	
	@Convert(converter=LocalDateTimeAttributeConverter.class)
	private LocalDateTime expiration_time;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	User user;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public LocalDateTime getExpiration_time() {
		return expiration_time;
	}
	public void setExpiration_time(LocalDateTime expiration_time) {
		this.expiration_time = expiration_time;
	}
	public UserToken(String token, LocalDateTime creatingTime, User user,int expiration_time_in_min) {
		this.token = token;
		this.expiration_time = creatingTime.plusMinutes(expiration_time_in_min);
		this.user = user;
	}
	
	

}

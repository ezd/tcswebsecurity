package com.example.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User implements Serializable,UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Id
	@GeneratedValue
	private Long id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String street;
	private String zipCode;
	private String state;
	private String country;
	private String nationality;
	private String passportNumber;
	private String dateOfBirth;
	private String phone;
	
	@OneToOne(mappedBy="user",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private UserToken userToken;
	
	public UserToken getUserToken() {
		return userToken;
	}
	public void setUserToken(UserToken userToken) {
		this.userToken = userToken;
	}
	private boolean enabled;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="role_user",joinColumns=@JoinColumn(name="user_id"),inverseJoinColumns=@JoinColumn(name="role_id"))
	private Set<Role> roles=new HashSet<>();
	
	
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getPassportNumber() {
		return passportNumber;
	}
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String userName,String password, String firstName, String lastName, String street, String zipCode, String city,
			String country, String dateOfBirth, String phone, boolean enabled) {
		super();
		this.username = userName;
		this.password=password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.zipCode = zipCode;
		this.state = city;
		this.country = country;
		this.dateOfBirth = dateOfBirth;
		this.phone = phone;
		this.enabled=enabled;
		
	}
	
	
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Authority> autorities=new HashSet<>();
//		for(Role r:this.getRoles()){
//			autorities.add(new Authority(r.getName()));
//		}
		this.getRoles().forEach(ur->autorities.add(new Authority(ur.getName())));
		return autorities;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", street=" + street + ", zipCode=" + zipCode + ", state=" + state
				+ ", country=" + country + ", nationality=" + nationality + ", passportNumber=" + passportNumber
				+ ", dateOfBirth=" + dateOfBirth + ", phone=" + phone + ", userToken=" + userToken + ", enabled="
				+ enabled + ", roles=" + roles + "]";
	}


}

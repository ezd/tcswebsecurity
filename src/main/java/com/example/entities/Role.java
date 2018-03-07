package com.example.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.example.enums.RoleEnum;

@Entity
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
//	@ManyToMany(mappedBy="roles",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
//	private Set<User> users=new HashSet<>();
	
	@OneToMany(mappedBy="role")
	List<User> users;
	
	public Role() {
		// TODO Auto-generated constructor stub
	}
	
	public Role(RoleEnum roleEnum) {
		super();
		this.name = roleEnum.getRoleName();
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}


	
	
	
}

package com.example.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Role;
@Repository
public interface RoleRepo extends CrudRepository<Role, Long> {

	Role findByName(String name);

}

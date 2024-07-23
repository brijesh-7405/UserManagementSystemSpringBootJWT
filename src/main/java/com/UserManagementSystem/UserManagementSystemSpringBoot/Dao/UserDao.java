package com.UserManagementSystem.UserManagementSystemSpringBoot.Dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.UserManagementSystem.UserManagementSystemSpringBoot.Bean.User;


public interface UserDao extends JpaRepository<User,Integer>{
	
	public List<User> findByEmail(String email); 
	
	public List<User> findByRole(String role);
	
	public List<User> findByUserID(int userid);
	
}

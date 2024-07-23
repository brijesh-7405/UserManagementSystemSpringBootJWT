package com.UserManagementSystem.UserManagementSystemSpringBoot.Dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.UserManagementSystem.UserManagementSystemSpringBoot.Bean.UserAddress;

public interface UserAddressDao extends JpaRepository<UserAddress, Integer> {
	
	@Query("from UserAddress where user_userID=?1")
	public List<UserAddress> getUserAddress(int userid);
}




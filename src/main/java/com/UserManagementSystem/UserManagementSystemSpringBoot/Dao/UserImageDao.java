package com.UserManagementSystem.UserManagementSystemSpringBoot.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.UserManagementSystem.UserManagementSystemSpringBoot.Bean.UserImage;

public interface UserImageDao extends JpaRepository<UserImage,Integer>{}

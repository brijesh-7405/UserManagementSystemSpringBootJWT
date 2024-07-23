package com.UserManagementSystem.UserManagementSystemSpringBoot.Service;

import com.UserManagementSystem.UserManagementSystemSpringBoot.Bean.User;
import com.UserManagementSystem.UserManagementSystemSpringBoot.Bean.UserAddress;

import java.util.*;
public interface UserService{
	public boolean userExist(String mail);
	public void registerUser(User user);
	public User checkUser(String email);
	public List<User> getUsers();
	public void deleteUser(int userid);
	public void changePwd(User user);
	public void updateUserProfile(User user);
	public User getUserDetails(int userID);
	public List<UserAddress> getUserAddress(int userid);
	public void deleteAddress(UserAddress address,User user);
	public void deleteImage(int imageid,int userid);
}

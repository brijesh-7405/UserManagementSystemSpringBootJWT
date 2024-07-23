package com.UserManagementSystem.UserManagementSystemSpringBoot.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.UserManagementSystem.UserManagementSystemSpringBoot.Bean.User;
import com.UserManagementSystem.UserManagementSystemSpringBoot.Bean.UserAddress;
import com.UserManagementSystem.UserManagementSystemSpringBoot.Bean.UserImage;
import com.UserManagementSystem.UserManagementSystemSpringBoot.Dao.UserAddressDao;
import com.UserManagementSystem.UserManagementSystemSpringBoot.Dao.UserDao;
import com.UserManagementSystem.UserManagementSystemSpringBoot.Dao.UserImageDao;
import com.UserManagementSystem.UserManagementSystemSpringBoot.UtilityClass.EncryptPwd;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
	@InjectMocks
	private UserServiceImpl userservice;

	@Mock
	private UserDao userdao;

	@Mock
	private UserAddressDao useraddressdao;

	@Mock
	private UserImageDao userimagedao;

	private User user;

	private UserAddress add;

	private UserImage userimage;

	@BeforeEach
	void setUp() throws Exception {
		user = new User();
		add = new UserAddress();
		userimage = new UserImage();
		user.setFirstname("Brijesh");
		user.setLastname("Rajput");
		user.setEmail("abc@gmail.com");
		user.setGender("Male");
		user.setAnswer1("Hello");
		user.setAnswer2("Hello");
		user.setDateofbirth("22/03/2000");
		user.setLanguage("English");
		String pwd = EncryptPwd.encryption("12345");
		user.setPassword(pwd);
		user.setPhone(9898990074L);
		user.setRole("user");
		user.setUserID(1);
		List<UserAddress> addlist = new ArrayList<UserAddress>();
		add.setAdd1("Narol");
		add.setAdd2("NarolRoad");
		add.setAddressid(2);
		add.setCity("Ahmedabad");
		add.setCountry("India");
		add.setState("Gujarat");
		add.setPincode("382405");
		add.setUser(user);
		addlist.add(add);
		user.setAddress(addlist);
		List<UserImage> imglist = new ArrayList<UserImage>();
		userimage.setImgid(3);
		userimage.setUser(user);
		BufferedImage bufferimage = ImageIO.read(new File("C:\\Users\\BRIJESH RAJPUT\\Downloads\\download.png"));
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ImageIO.write(bufferimage, "png", output);
		byte[] data = output.toByteArray();
		userimage.setImgbytes(data);
		imglist.add(userimage);
		user.setPic(imglist);
	}

	@Test
	void testRegisterUser() {
		userservice.registerUser(user);
		verify(userdao, atLeastOnce()).save(user);
	}

	@Test
	void testUserExist1() {
		boolean status = userservice.userExist("ab@gmail.com");
		assertNotNull(status);
	}

	@Test
	void testUserExist2() {
		List<User> usrlist = new ArrayList<User>();
		usrlist.add(user);
		when(userdao.findByEmail(user.getEmail())).thenReturn(usrlist);
		boolean status = userservice.userExist("abc@gmail.com");
		assertNotNull(status);
	}

	@Test
	void testCheckUser1() {
		List<User> usrlist = new ArrayList<User>();
		usrlist.add(user);
		when(userdao.findByEmail(user.getEmail())).thenReturn(usrlist);
		User usr = userservice.checkUser(user.getEmail());
		assertNotNull(usr);
	}

	@Test
	void testChangePwd() {
		userservice.changePwd(user);
		verify(userdao, atLeastOnce()).save(user);
	}

	@Test
	void testGetUsers() {
		List<User> usrlist = new ArrayList<User>();
		usrlist.add(user);
		when(userdao.findByRole(user.getRole())).thenReturn(usrlist);
		List<User> getusersList = userservice.getUsers();
		assertNotNull(getusersList);
	}

	@Test
	void testDeleteUser() {
		userservice.deleteUser(user.getUserID());
		verify(userdao, atLeastOnce()).deleteById(user.getUserID());
	}

	@Test
	void testUpdateUserProfile() {
		userservice.updateUserProfile(user);
		verify(userdao, atLeastOnce()).save(user);
	}

	@Test
	void testGetUserDetails() {
		List<User> usrlist = new ArrayList<User>();
		usrlist.add(user);
		when(userdao.findByUserID(user.getUserID())).thenReturn(usrlist);
		User usr = userservice.getUserDetails(user.getUserID());
		assertNotNull(usr);
	}

	@Test
	void testGetUserAddress() {
		List<UserAddress> usraddresslist = new ArrayList<UserAddress>();
		when(useraddressdao.getUserAddress(user.getUserID())).thenReturn(usraddresslist);
		List<UserAddress> getAddressesList = userservice.getUserAddress(user.getUserID());
		assertNotNull(getAddressesList);
	}

	@Test
	void testDeleteAddress1() {
		userservice.deleteAddress(add, user);
		verify(useraddressdao, atLeastOnce()).delete(add);
	}
	@Test
	void testDeleteAddress2() {
		UserAddress newAdd = new UserAddress();
		newAdd.setAddressid(1233);
		userservice.deleteAddress(newAdd, user);
		verify(useraddressdao, atLeastOnce()).delete(newAdd);
	}

	@Test
	void testDeleteImage() {
		List<User> usrlist = new ArrayList<User>();
		usrlist.add(user);
		when(userdao.findByUserID(user.getUserID())).thenReturn(usrlist);
		User userRequired = usrlist.get(0);
		UserImage userimg = userRequired.getPic().get(0);
		userservice.deleteImage(userimg.getImgid(), user.getUserID());
		verify(userimagedao, atLeastOnce()).deleteById(userimg.getImgid());
	}
	@Test
	void testDeleteImage2() {
		List<User> usrlist = new ArrayList<User>();
		usrlist.add(user);
		when(userdao.findByUserID(user.getUserID())).thenReturn(usrlist);
		userservice.deleteImage(1222, user.getUserID());
		verify(userimagedao, atLeastOnce()).deleteById(1222);
	}

}

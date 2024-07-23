package com.UserManagementSystem.UserManagementSystemSpringBoot.Controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.UserManagementSystem.UserManagementSystemSpringBoot.Bean.User;
import com.UserManagementSystem.UserManagementSystemSpringBoot.Bean.UserAddress;
import com.UserManagementSystem.UserManagementSystemSpringBoot.Bean.UserImage;
import com.UserManagementSystem.UserManagementSystemSpringBoot.Service.UserService;


@WebMvcTest(MVCController.class)
class MVCControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userservice;

	private User user;

	private UserAddress add;
	
	private UserImage userimage;
	
	ByteArrayOutputStream output;
	List<UserAddress> addlist;
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
		user.setPassword("Brijesh163@");
		user.setPhone(9898990074L);
		user.setRole("admin");
		user.setUserID(1);
		addlist = new ArrayList<UserAddress>();
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
		output = new ByteArrayOutputStream();
		ImageIO.write(bufferimage, "png", output);
		byte[] data = output.toByteArray();
		userimage.setImgbytes(data);
		imglist.add(userimage);
		user.setPic(imglist);
	}

	@Test
	void testIndex() throws Exception {
		mockMvc.perform(get("/"));
	}

	@Test
	void testRegister() throws Exception {
		this.mockMvc.perform(get("/registration"));
	}

	@Test
	void testForgotpwd() throws Exception {
		this.mockMvc.perform(get("/forgotpwd"));
	}
	@Test
	void testRegisterUser1() throws Exception {
		mockMvc.perform(multipart("/userRegistration")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaType.MULTIPART_FORM_DATA)
				.flashAttr("user", user)
				.sessionAttr("USER", user)
				.param("repass","Brijesh163@"))
	.andExpect(redirectedUrl("adminWork"));
	}
	@Test
	void testRegisterUser1a() throws Exception {		
		FileInputStream filein = new FileInputStream(new File("C:\\Users\\BRIJESH RAJPUT\\Downloads\\download.png"));
		
		MockMultipartFile mockfilein = new MockMultipartFile("image[]", "abcd.jpg", "multipart/form-data",filein);
		
		mockMvc.perform(multipart("/userRegistration").file(mockfilein)
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaType.MULTIPART_FORM_DATA)
				.flashAttr("user", user)
				.sessionAttr("USER", user)
				.param("repass","Brijesh163@"))
	.andExpect(redirectedUrl("adminWork"));
	}
	
	@Test
	void testRegisterUser2() throws Exception {
		user.setRole("user");
		mockMvc.perform(post("/userRegistration")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaType.MULTIPART_FORM_DATA)
				.flashAttr("user", user)
				.param("repass","Brijesh163@"))
	.andExpect(redirectedUrl("index"));
	}
	
	@Test
	void testRegisterUser3() throws Exception {
		user.setLastname("1234");
		user.setPic(null);
		user.setRole("user");
		mockMvc.perform(post("/userRegistration")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaType.MULTIPART_FORM_DATA)
				.flashAttr("user", user)
				.param("repass","Brijesh163@"));
	}
	@Test
	void testRegisterUser4() throws Exception {
		user.setPassword("Brijesh163@");
		user.setRole("user");
		user.setPic(null);
		mockMvc.perform(post("/userRegistration")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaType.MULTIPART_FORM_DATA)
				.flashAttr("user", user)
				.param("repass","Brijesh13@"));
	}
	@Test
	void testRegisterUser5() throws Exception {
		user.setEmail("");
		user.setRole("user");
		user.setPic(null);
		mockMvc.perform(post("/userRegistration")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaType.MULTIPART_FORM_DATA)
				.flashAttr("user", user)
				.param("repass","Brijesh13@"));
	}
	@Test
	void testRegisterUser6() throws Exception {
		user.setPassword("");
		user.setRole("user");
		user.setPic(null);
		mockMvc.perform(post("/userRegistration")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaType.MULTIPART_FORM_DATA)
				.flashAttr("user", user)
				.param("repass","Brijesh13@"));
	}
	@Test
	void testRegisterUser7() throws Exception {
		user.setPhone(12345L);
		user.setRole("user");
		user.setPic(null);
		mockMvc.perform(post("/userRegistration")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaType.MULTIPART_FORM_DATA)
				.flashAttr("user", user)
				.param("repass","Brijesh13@"));
	}
	@Test
	void testRegisterUser8() throws Exception {
		user.setPassword("123@");
		user.setRole("user");
		user.setPic(null);
		mockMvc.perform(post("/userRegistration")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaType.MULTIPART_FORM_DATA)
				.flashAttr("user", user)
				.param("repass","Brijesh13@"));
	}
	@Test
	void testRegisterUser9() throws Exception {
		user.setRole("user");
		user.setPic(null);
		when(userservice.userExist(user.getEmail())).thenReturn(true);
		mockMvc.perform(post("/userRegistration")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaType.MULTIPART_FORM_DATA)
				.flashAttr("user", user)
				.param("repass","Brijesh163@"));
	}
//	@Test
//	void testRegisterUser3() throws Exception {
//		String msg="valid";
//		when(val.validData(user, "Brijesh163@")).thenReturn(msg);
//		user.setRole("user");
//		mockMvc.perform(post("/userRegistration")
//				.contentType(MediaType.MULTIPART_FORM_DATA)
//				.accept(MediaType.MULTIPART_FORM_DATA)
//				.flashAttr("user", user)
//				.sessionAttr("USER", user)
//				.param("repass","Brijesh163@"))
//	.andExpect(redirectedUrl("userDashBoard"));
//	}
//	@Test
//	void testRegisterUser4() throws Exception {
//		String msg="valid";
//		when(val.validData(user, "Brijesh163@")).thenReturn(msg);
//		mockMvc.perform(multipart("/userRegistration")
//				.contentType(MediaType.MULTIPART_FORM_DATA)
//				.accept(MediaType.MULTIPART_FORM_DATA)
//				.flashAttr("user", user)
//				.param("repass","Brijesh163@"))
//	.andExpect(redirectedUrl("index"));
//	}

//	@Test
//	void testRegisterUser1() throws Exception {
//		String msg="valid";
//		when(val.validData(user, "Brijesh163@")).thenReturn(msg);
//		mockMvc.perform(multipart("/userRegistration")
//				.contentType(MediaType.MULTIPART_FORM_DATA)
//				.accept(MediaType.MULTIPART_FORM_DATA)
//				.flashAttr("user", user)
//				.sessionAttr("USER", user)
//				.param("repass","Brijesh163@"))
//	.andExpect(redirectedUrl("adminWork"));
//	}
//	@Test
//	void testRegisterUser2() throws Exception {
//		String msg="valid";
//		when(val.validData(user, "Brijesh163@")).thenReturn(msg);
//		mockMvc.perform(multipart("/userRegistration")
//				.contentType(MediaType.MULTIPART_FORM_DATA)
//				.accept(MediaType.MULTIPART_FORM_DATA)
//				.flashAttr("user", user)
//				.param("repass","Brijesh163@"))
//	.andExpect(redirectedUrl("index"));
//	}
//	@Test
//	void testRegisterUser2() throws Exception {
//		String pwd = encrypt.encryption("12345");
//		user.setRole("user");
//		mockMvc.perform(post("/userRegistration")
//				.contentType(MediaType.MULTIPART_FORM_DATA)
//				.accept(MediaType.MULTIPART_FORM_DATA)
//				.flashAttr("user", user)
//				.sessionAttr("USER", user)
//				.param("repass",pwd))
//	.andExpect(redirectedUrl("index"));
//	}
//	@Test
//	void testRegisterUser3() throws Exception {
//		String pwd = encrypt.encryption("12345");
//		mockMvc.perform(post("/userRegistration")
//				.contentType(MediaType.MULTIPART_FORM_DATA)
//				.accept(MediaType.MULTIPART_FORM_DATA)
//				.flashAttr("user", user)
//				.param("repass",pwd))
//	.andExpect(redirectedUrl("index"));
//	}

	@Test
	void testCheckuserexist1() throws Exception {
		boolean status = true;
		when(userservice.userExist(user.getEmail())).thenReturn(status);
		mockMvc.perform(post("/checkUserExistDone").param("email",user.getEmail()))
		.andExpect(content().string("*Email already exist"));
	}
	@Test
	void testCheckuserexist2() throws Exception {
		boolean status = false;
		when(userservice.userExist(user.getEmail())).thenReturn(status);
		mockMvc.perform(post("/checkUserExistDone").param("email",user.getEmail()))
		.andExpect(content().string(""));
	}

	@Test
	void testLogin1() throws Exception{
		String pwd = "da57712a08b7f84f28b3e9bd3ed8be34";
		user.setRole("user");
		user.setPassword(pwd);
		when(userservice.checkUser(user.getEmail())).thenReturn(user);
		mockMvc.perform(post("/loginServlet")
				.param("email", user.getEmail())
				.param("password","Brijesh163@"))
		.andExpect(redirectedUrl("userDashBoard"));;
	}
	@Test
	void testLogin2() throws Exception{
		String pwd = "da57712a08b7f84f28b3e9bd3ed8be34";
		user.setPassword(pwd);
		when(userservice.checkUser(user.getEmail())).thenReturn(user);
		mockMvc.perform(post("/loginServlet")
				.param("email", user.getEmail())
				.param("password","Brijesh163@"))
		.andExpect(redirectedUrl("adminWork"));
	}
	@Test
	void testLogin3() throws Exception{
		when(userservice.checkUser(user.getEmail())).thenReturn(user);
		mockMvc.perform(post("/loginServlet")
				.param("email", user.getEmail())
				.param("password",user.getPassword()))
		.andExpect(view().name("index"));
	}
	@Test
	void testLogin4() throws Exception{
		User userRequired =null;
		when(userservice.checkUser(user.getEmail())).thenReturn(userRequired);
		mockMvc.perform(post("/loginServlet")
				.param("email", user.getEmail())
				.param("password",user.getPassword()))
		.andExpect(view().name("index"));
	}

	@Test
	void testAdminPanel() throws Exception {
		mockMvc.perform(post("/adminWork")
				.sessionAttr("USER", user))
		.andExpect(view().name("adminDashBoard"));
	}

	@Test
	void testUserDashBoard() throws Exception {
		mockMvc.perform(get("/userDashBoard").sessionAttr("USER", user));
	}

	@Test
	void testLogout2() throws Exception {
		mockMvc.perform(get("/logOut")).andExpect(redirectedUrl("index"));
	}

	@Test
	void testAfterforgotpwd1() throws Exception {
		when(userservice.checkUser(user.getEmail())).thenReturn(user);
		mockMvc.perform(post("/forgotPwd")
				.param("email", user.getEmail())
				.param("birthdate",user.getDateofbirth())
				.param("q1",user.getAnswer1())
				.param("q2",user.getAnswer2()))
		.andExpect(view().name("resetpwd"));
	}
	@Test
	void testAfterforgotpwd2A() throws Exception {
		when(userservice.checkUser(user.getEmail())).thenReturn(user);
		mockMvc.perform(post("/forgotPwd")
				.param("email", user.getEmail())
				.param("birthdate",user.getDateofbirth())
				.param("q1","done")
				.param("q2",user.getAnswer2()))
		.andExpect(view().name("forgotpwd"));
	}
	@Test
	void testAfterforgotpwd2B() throws Exception {
		when(userservice.checkUser(user.getEmail())).thenReturn(user);
		mockMvc.perform(post("/forgotPwd")
				.param("email", user.getEmail())
				.param("birthdate",user.getDateofbirth())
				.param("q1",user.getAnswer1())
				.param("q2","done"))
		.andExpect(view().name("forgotpwd"));
	}
	@Test
	void testAfterforgotpwd2C() throws Exception {
		when(userservice.checkUser(user.getEmail())).thenReturn(user);
		mockMvc.perform(post("/forgotPwd")
				.param("email", user.getEmail())
				.param("birthdate",user.getDateofbirth())
				.param("q1","done")
				.param("q2","hum"))
		.andExpect(view().name("forgotpwd"));
	}
	@Test
	void testAfterforgotpwd3() throws Exception {
		when(userservice.checkUser(user.getEmail())).thenReturn(user);
		mockMvc.perform(post("/forgotPwd")
				.param("email", user.getEmail())
				.param("birthdate","12/12/2000")
				.param("q1",user.getAnswer1())
				.param("q2",user.getAnswer2()))
		.andExpect(view().name("forgotpwd"));
	}
	@Test
	void testAfterforgotpwd4() throws Exception {
		User userRequired =null;
		when(userservice.checkUser(user.getEmail())).thenReturn(userRequired);
		mockMvc.perform(post("/forgotPwd")
				.param("email", user.getEmail())
				.param("birthdate",user.getDateofbirth())
				.param("q1",user.getAnswer1())
				.param("q2",user.getAnswer2()))
		.andExpect(view().name("forgotpwd"));
	}

	@Test
	void testResetpwd() throws Exception {
		mockMvc.perform(post("/resetpwd"));
	}

	@Test
	void testChangepwd() throws Exception {
		when(userservice.checkUser(user.getEmail())).thenReturn(user);
		mockMvc.perform(post("/resetPassword")
				.param("usermail", user.getEmail())
				.param("password",user.getPassword()))
		.andExpect(redirectedUrl("index"));
	}

	@Test
	void testDeleteUser() throws Exception {
		mockMvc.perform(post("/deleteUser")
				.param("userid", String.valueOf(user.getUserID())));
	}

	@Test
	void testGoingToEdit1() throws Exception {
		user.setRole("user");
		user.setPic(null);
		mockMvc.perform(post("/userDetails")
				.sessionAttr("USER", user))
		.andExpect(view().name("registration"));
	}
	@Test
	void testGoingToEdit2() throws Exception {
		mockMvc.perform(post("/userDetails")
				.sessionAttr("USER", user)
				.param("userid", String.valueOf(user.getUserID())))
		.andExpect(view().name("registration"));
	}

	@Test
	void testEdit1() throws Exception {
		FileInputStream filein = new FileInputStream(new File("C:\\Users\\BRIJESH RAJPUT\\Downloads\\download.png"));
		
		MockMultipartFile mockfilein = new MockMultipartFile("image[]", "abcd.jpg", "multipart/form-data",filein);
		when(userservice.getUserAddress(user.getUserID())).thenReturn(addlist);
		when(userservice.getUserDetails(user.getUserID())).thenReturn(user);
		mockMvc.perform(multipart("/editServlet").file(mockfilein)
				.flashAttr("user", user)
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaType.MULTIPART_FORM_DATA)
		.param("addressid", String.valueOf(add.getAddressid())))
		.andExpect(redirectedUrl("userData"));
	}
	@Test
	void testEdit2() throws Exception {
		FileInputStream filein = new FileInputStream(new File("C:\\Users\\BRIJESH RAJPUT\\Downloads\\download.png"));
		
		MockMultipartFile mockfilein = new MockMultipartFile("image[]", "abcd.jpg", "multipart/form-data",filein);
		when(userservice.getUserAddress(user.getUserID())).thenReturn(addlist);
		when(userservice.getUserDetails(user.getUserID())).thenReturn(user);
		mockMvc.perform(multipart("/editServlet").file(mockfilein)
				.flashAttr("user", user)
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaType.MULTIPART_FORM_DATA)
		.param("addressid", String.valueOf(23)))
		.andExpect(redirectedUrl("userData"));
	}
	@Test
	void testEdit3() throws Exception {
		FileInputStream filein = new FileInputStream(new File("C:\\Users\\BRIJESH RAJPUT\\Downloads\\download.png"));
		
		MockMultipartFile mockfilein = new MockMultipartFile("image[]", "abcd.jpg", "multipart/form-data",filein);
		when(userservice.getUserAddress(user.getUserID())).thenReturn(addlist);
		when(userservice.getUserDetails(user.getUserID())).thenReturn(user);
		mockMvc.perform(multipart("/editServlet").file(mockfilein)
				.flashAttr("user", user)
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaType.MULTIPART_FORM_DATA)
		.param("addressid", ""))
		.andExpect(redirectedUrl("userData"));
	}
	@Test
	void testEdit4() throws Exception {
		FileInputStream filein = new FileInputStream(new File("C:\\Users\\BRIJESH RAJPUT\\Downloads\\download.png"));
		
		MockMultipartFile mockfilein = new MockMultipartFile("image[]", "abcd.jpg", "multipart/form-data",filein);
		when(userservice.getUserAddress(user.getUserID())).thenReturn(addlist);
		when(userservice.getUserDetails(user.getUserID())).thenReturn(user);
		mockMvc.perform(multipart("/editServlet").file(mockfilein)
				.flashAttr("user", user)
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaType.MULTIPART_FORM_DATA)
		.param("addressid", "0"))
		.andExpect(redirectedUrl("userData"));
	}
	@Test
	void testEdit5() throws Exception {
		FileInputStream filein = new FileInputStream(new File("C:\\Users\\BRIJESH RAJPUT\\Downloads\\download.png"));
		
		MockMultipartFile mockfilein = new MockMultipartFile("image[]", "abcd.jpg", "multipart/form-data",filein);
		user.setLastname("12344");
		user.setPic(null);
		when(userservice.getUserAddress(user.getUserID())).thenReturn(addlist);
		when(userservice.getUserDetails(user.getUserID())).thenReturn(user);
		mockMvc.perform(multipart("/editServlet").file(mockfilein)
				.flashAttr("user", user)
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaType.MULTIPART_FORM_DATA)
		.param("addressid", "0"))
		.andExpect(view().name("registration"));
	}

	@Test
	void testUserData1() throws Exception {
		mockMvc.perform(post("/userData")
				.sessionAttr("USER", user))
				.andExpect(redirectedUrl("adminWork"));
	}
	@Test
	void testUserData2() throws Exception {
		user.setRole("user");
		mockMvc.perform(post("/userData")
				.sessionAttr("USER", user))
				.andExpect(redirectedUrl("userDashBoard"));
	}

	@Test
	void testDeleteUserImage() throws Exception {
		mockMvc.perform(post("/removeImage")
				.param("imgId",String.valueOf("3"))
				.param("userid",String.valueOf(user.getUserID())));
	}

}

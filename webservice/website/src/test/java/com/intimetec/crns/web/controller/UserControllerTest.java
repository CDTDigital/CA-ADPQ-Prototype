package com.intimetec.crns.web.controller;

import com.google.gson.Gson;
import com.intimetec.crns.core.models.User;
import com.intimetec.crns.core.models.UserLocation;
import com.intimetec.crns.core.models.UserNotificationOptions;
import com.intimetec.crns.util.Utils;

import com.intimetec.crns.web.Application;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.annotation.SessionScope;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author In Time Tec
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@SessionScope
public class UserControllerTest {

  /**
   * To log the application messages. 
   */
  private static final Logger LOGGER = 
		  LoggerFactory.getLogger(UserControllerTest.class);

  /**
   * Instance of the class {@link MockMvc}.
   */
  @Autowired
  private MockMvc mockMvc;
 
  private static String token;
  private MvcResult response;
  private static int testcase;
  
  /*
   * Running this setup before each test case to get authToken, 
   * change the JSON data for user who has registered.
   */
  @Before
  public void setup() throws Exception {
  String json = "{\"userName\":\"test\",\"password\":\"123456\","
  				+ "\"deviceId\":\"1\","
                + "\"deviceType\":\"IOS\",\"deviceToken\":\"uyfiu86976986\"}";
  
  this.mockMvc.perform(post("/login")
              .contentType(MediaType.APPLICATION_JSON)
              .content(json))
              .andExpect(status().isOk());
  response = this.mockMvc.perform((post("/login").content(json)))
             .andReturn();
  String loginToken = Utils.removeParenthesis(response.getResponse().
		  getContentAsString());
  token = loginToken.substring(loginToken.lastIndexOf(
		  "authToken:")).replaceAll("authToken:", "");
  LOGGER.info("Testcase : User Login Successfuly " + token);
  }
  
  /*
   * Running this test case the second time will eventually fail as 
   * the existing user can't be created twice.
   */
  @Test
  public void testUserCreation() throws Exception {
	  
	 Gson gson = new Gson();
	 UserLocation location = new UserLocation();
	 location.setAddressLine1("39");
	 location.setAddressLine2("State Street");
	 location.setCity("Commerce");
	 location.setLatitude("34.2040");
	 location.setLongitude("-83.4571");
	 location.setZipCode("30529");
	 location.setPlaceId("ChIJ2XMUTfwE9ogRdU5NBLNmsIA");
	 location.setCurrentLocation(true);
	 
	 User user = new User();
	 user.setFirstName("test" + (Math.random() * 100 + 1));
	 user.setLastName("user");
	 user.setEmail("test" + (Math.random() * 100 + 1) + "@gmail.com");
	 user.setMobileNo("1234567890");
	 user.setUserName("test" + (Math.random() * 100 + 1) );
	 user.setPassword("crnsuser");
	 
	 String json = (gson.toJson(user)).replaceAll("}", ",").
			 concat("\"location\" :" + gson.toJson(location) + "}");
	  
     this.mockMvc.perform(post("/users/createUser")
    		    .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
 
     LOGGER.info("Testcase " + ++testcase + ":User Created Successfuly ");
  }
  
  @Test
  public void testUserProfile() throws Exception {
    this.mockMvc.perform(get("/users/getProfile")
                .header("authToken", token))
                .andExpect(status().isOk());
    LOGGER.info("Testcase " + ++testcase + ": Getting current user Profile");
  }
   
  @Test
  public void testUserAccountSetup() throws Exception {
	  Gson gson = new Gson();
	  UserLocation location = new UserLocation();
	  location.setAddressLine1("39");
	  location.setAddressLine2("State Street");
	  location.setCity("Commerce");
	  location.setLatitude("34.2040");
	  location.setLongitude("-83.4571");
	  location.setZipCode("30529");
	  location.setPlaceId("ChIJ2XMUTfwE9ogRdU5NBLNmsIA");
	  location.setCurrentLocation(true);  

	  UserNotificationOptions userNotificationOptions = 
			  new UserNotificationOptions();
	  userNotificationOptions.setSendSms(true);
	  userNotificationOptions.setSendEmail(true);
	  userNotificationOptions.setSendPushNotification(true);
	  userNotificationOptions.setLiveLocationTracking(true);

	  User user = new User();
	  user.setFirstName("test");
	  user.setLastName("user");
	  user.setEmail("test@gmail.com");
	  user.setMobileNo("1234567890");
	  user.setEnabled(true);

	  String json = (gson.toJson(user)).replaceAll("}", ",").
			  concat("\"location\": "
	  		        + gson.toJson(location).concat(
	  		        		",\"userNotificationOptions\":"
	  		        + gson.toJson(userNotificationOptions)) + "}");
	  LOGGER.info("JSON DATA" + json);
	  this.mockMvc.perform(post("/users/setProfile")
  		    .contentType(MediaType.APPLICATION_JSON)
              .content(json).header("authToken", token))
              .andExpect(status().isOk());

	  LOGGER.info("Testcase " + ++testcase + ":User Account setup completed ");
  }
  
  @Test
  public void testUserNotificationOption() throws Exception {
    this.mockMvc.perform(get("/users/getNotificationOptions")
                .header("authToken", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists());
    LOGGER.info("Testcase " + ++testcase + ": "
    		+ "Getting current user Notifications");
  }
  
  @Test
  public void testUserCurrentLocation() throws Exception {
	LOGGER.info("Testcase " + ++testcase + ": Getting current user Location");
    this.mockMvc.perform(get("/users/getCurrentLocation")
    		    .contentType(MediaType.APPLICATION_JSON)
                .header("authToken", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists());
    LOGGER.info("Testcase " + ++testcase + ": Getting current user Location");
  }
  
  @Test
  public void testUserProfileLocation() throws Exception {
    this.mockMvc.perform(get("/users/getProfileLocation")
    		    .contentType(MediaType.APPLICATION_JSON)
                .header("authToken", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists());
    LOGGER.info("Testcase " + ++testcase + ": "
    		+ "Getting current user profile Location");
  }  
}
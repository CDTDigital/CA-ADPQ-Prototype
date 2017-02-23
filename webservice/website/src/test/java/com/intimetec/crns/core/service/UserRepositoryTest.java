package com.intimetec.crns.core.service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intimetec.crns.core.models.User;
import com.intimetec.crns.core.service.user.UserService;
import com.intimetec.crns.core.stubs.JsonUtils;
import com.intimetec.crns.web.Application;
import com.intimetec.crns.web.controller.UsersController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class UserRepositoryTest {
	@Autowired
	private MockMvc mockMvc;

	@Mock
	private UserService userService;

	@InjectMocks
	private UsersController usersController;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
	}

	@Test
	public void testGetAllUsers() throws Exception {
		ObjectMapper mapper = JsonUtils.createMapper(true);
		
		JsonNode jsonData = JsonUtils.getRootNode(mapper);
		
		List<User> users = new ArrayList<User>();
		JsonNode usersNode = jsonData.get("user");
		if(usersNode.isArray()){
		    for (final JsonNode userNode : usersNode) {
		        users.add(mapper.treeToValue(userNode, User.class));
		    }
		}
		
		Mockito.when(userService.getAllUsers()).thenReturn(users);
		this.mockMvc.perform(get("/users")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

	}
}

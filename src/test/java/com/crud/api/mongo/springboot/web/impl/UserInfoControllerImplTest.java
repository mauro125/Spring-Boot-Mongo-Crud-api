package com.crud.api.mongo.springboot.web.impl;

import com.crud.api.mongo.springboot.constants.MessageCodes;
import com.crud.api.mongo.springboot.dto.UserDTO;
import com.crud.api.mongo.springboot.model.Address;
import com.crud.api.mongo.springboot.model.User;
import com.crud.api.mongo.springboot.repository.UserRepository;
import com.crud.api.mongo.springboot.service.impl.UserServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserInfoControllerImpl.class)
class UserInfoControllerImplTest {

    private static final List<UserDTO> mockUsers = new ArrayList<UserDTO>();

    private static final List<UserDTO> mockUsersPagination = new ArrayList<UserDTO>();
    private static UserDTO mockUser;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserServiceImpl userService;
    @MockBean
    private UserRepository UserRepository;

    @BeforeAll
    static void creatMockUsers() {
        Address mockAddress = new Address("123 Johnson st", "Tamarac", "FL", "US");
        mockUser = new UserDTO("1", "John", "Doe", "+2", "1234567890", "123@gmail.com", mockAddress, "project1");

        Address address1 = new Address("123 Johnson st", "Tamarac", "FL", "US");
        Address address2 = new Address("123 Johnson st", "Tamarac", "FL", "US");
        Address address3 = new Address("123 Johnson st", "Tamarac", "FL", "US");

        mockUsers.add(new UserDTO("1", "James", "Jones", "+1", "1234567890", "james@gmail.com", address1, "project1"));
        mockUsers.add(new UserDTO("2", "Wilson", "Smith", "+5", "0987654321", "wilson@gmail.com", address2, "project2"));
        mockUsers.add(new UserDTO("3", "Ted", "Harrison", "+11", "543219876", "ted@gmail.com", address3, "project3"));

        mockUsersPagination.add(new UserDTO("1", "James", "Jones", "+1", "1234567890", "james@gmail.com", address1, "project1"));
        mockUsersPagination.add(new UserDTO("2", "Wilson", "Smith", "+5", "0987654321", "wilson@gmail.com", address2, "project2"));
    }

    char randomPunctuationChar() {
        return RandomStringUtils.random(1, "~`!@#$%^&*()_-+=?><,./';:\"{}[]|\\").charAt(0);
    }

    @Test
    void getAllUsers() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(mockUsers);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/users").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "[{\"id\":\"1\",\"firstName\":\"James\",\"lastName\":\"Jones\",\"mobCtryCode\":\"+1\",\"mobNumber\":\"1234567890\",\"email\":\"james@gmail.com\",\"address\":{\"address\":\"123 Johnson st\",\"city\":\"Tamarac\",\"state\":\"FL\",\"country\":\"US\"}}," +
                "{\"id\":\"2\",\"firstName\":\"Wilson\",\"lastName\":\"Smith\",\"mobCtryCode\":\"+5\",\"mobNumber\":\"0987654321\",\"email\":\"wilson@gmail.com\",\"address\":{\"address\":\"123 Johnson st\",\"city\":\"Tamarac\",\"state\":\"FL\",\"country\":\"US\"}}," +
                "{\"id\":\"3\",\"firstName\":\"Ted\",\"lastName\":\"Harrison\",\"mobCtryCode\":\"+11\",\"mobNumber\":\"543219876\",\"email\":\"ted@gmail.com\",\"address\":{\"address\":\"123 Johnson st\",\"city\":\"Tamarac\",\"state\":\"FL\",\"country\":\"US\"}}" +
                "]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    void getUserById() throws Exception {
        Mockito.when(userService.getUserById(mockUser.id())).thenReturn(mockUser);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/users/1")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "{\"id\":\"1\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"mobCtryCode\":\"+2\",\"mobNumber\":\"1234567890\",\"email\":\"123@gmail.com\",\"address\":{\"address\":\"123 Johnson st\",\"city\":\"Tamarac\",\"state\":\"FL\",\"country\":\"US\"}}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    void getPaginatedUser() throws Exception {
        Mockito.when(userService.getAllUsersWithPagination(1, 2)).thenReturn(mockUsersPagination);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/users/all-with-pagination?pageNum=1&pageSize=2")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "[{\"id\":\"1\",\"firstName\":\"James\",\"lastName\":\"Jones\",\"mobCtryCode\":\"+1\",\"mobNumber\":\"1234567890\",\"email\":\"james@gmail.com\",\"address\":{\"address\":\"123 Johnson st\",\"city\":\"Tamarac\",\"state\":\"FL\",\"country\":\"US\"}}," +
                "{\"id\":\"2\",\"firstName\":\"Wilson\",\"lastName\":\"Smith\",\"mobCtryCode\":\"+5\",\"mobNumber\":\"0987654321\",\"email\":\"wilson@gmail.com\",\"address\":{\"address\":\"123 Johnson st\",\"city\":\"Tamarac\",\"state\":\"FL\",\"country\":\"US\"}}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    void createUser() throws Exception {
        Mockito.when(userService.saveUser(Mockito.any(User.class))).thenReturn(mockUser.id());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"mobCtryCode\":\"+2\",\"mobNumber\":\"1234567890\",\"email\":\"123@gmail.com\",\"address\":{\"address\":\"123 Johnson st\",\"city\":\"Tamarac\",\"state\":\"FL\",\"country\":\"US\"},\"password\":\"password123\"}")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("http://localhost/api/v1/users/1", response.getHeader(HttpHeaders.LOCATION));
    }

    @Test
    void createUserFailInvalidFirstName() throws Exception {
        mockMvc.perform(post("/api/v1/users").accept(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"firstName\":\"Joh" + randomPunctuationChar() + "n\",\"lastName\":\"Doe\",\"mobCtryCode\":\"+2\",\"mobNumber\":\"1234567890\",\"email\":\"123@gmail.com\",\"address\":{\"address\":\"123 Johnson st\",\"city\":\"Tamarac\",\"state\":\"FL\",\"country\":\"US\"},\"password\":\"password123\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(MessageCodes.INVALID_FNAME.getMessage())));
    }

    @Test
    void createUserFailInvalidLastName() throws Exception {
        mockMvc.perform(post("/api/v1/users").accept(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"firstName\":\"John\",\"lastName\":\"D" + randomPunctuationChar() + "oe\",\"mobCtryCode\":\"+2\",\"mobNumber\":\"1234567890\",\"email\":\"123@gmail.com\",\"address\":{\"address\":\"123 Johnson st\",\"city\":\"Tamarac\",\"state\":\"FL\",\"country\":\"US\"},\"password\":\"password123\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(MessageCodes.INVALID_LNAME.getMessage())));
    }

    @Test
    void updateUser() throws Exception {
        Mockito.when(userService.updateUser(Mockito.anyString(), Mockito.any(User.class))).thenReturn(mockUser.id());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/v1/users/1")
                .accept(MediaType.APPLICATION_JSON).content("{\"id\":\"1\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"mobCtryCode\":\"+2\",\"mobNumber\":\"1234567890\",\"email\":\"123@gmail.com\",\"address\":{\"address\":\"123 Johnson st\",\"city\":\"Tamarac\",\"state\":\"FL\",\"country\":\"US\"},\"password\":\"password123\"}")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("http://localhost/api/v1/users/1", response.getHeader(HttpHeaders.LOCATION));
    }

    @Test
    void deleteUserById() throws Exception {
        Mockito.when(userService.getUserById(mockUser.id())).thenReturn(mockUser);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
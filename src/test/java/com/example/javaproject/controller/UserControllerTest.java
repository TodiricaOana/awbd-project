package com.example.javaproject.controller;

import com.example.javaproject.dto.UserDto;
import com.example.javaproject.mapper.UserMapper;
import com.example.javaproject.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
    private static final Long ID = 1L;
    private static final String EMAIL = "dummy@gmail.com";
    private static final String ADDRESS = "dummyAddress";
    private static final String PASSWORD = "dummyPassword";
    private static final String FULLNAME = "dummyFullname";
    private static final String PHONENUMBER = "dummyPhoneNumber";

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Testing creating a user")
    void test_register() throws Exception {
        //Arrange
        UserDto dto = getUserDto();
        when(userService.createUser(any(), any())).thenReturn(dto);

        //Act
        MvcResult result = mockMvc.perform(post("/api/user/register")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(dto));
    }

    @Test
    @DisplayName("Testing get a user")
    void test_get() throws Exception {
        //Arrange
        UserDto dto = getUserDto();
        Long id = 1L;
        when(userService.findUserById(any())).thenReturn(dto);

        mockMvc.perform(get("/api/user/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.phoneNumber", is(dto.getPhoneNumber())))
                .andExpect(jsonPath("$.password", is(dto.getPassword())))
                .andExpect(jsonPath("$.address", is(dto.getAddress())))
                .andExpect(jsonPath("$.fullName", is(dto.getFullName())))
                .andExpect(jsonPath("$.email", is(dto.getEmail())));
    }

    @Test
    @DisplayName("Testing updating a user")
    void test_update() throws Exception {
        //Arrange
        UserDto dto = getUserDto();
        Long id = 1L;
        when(userService.updateUser(any(), any(), any())).thenReturn(dto);

        //Act
        MvcResult result = mockMvc.perform(put("/api/user/update/" + id)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(dto));
    }

    @Test
    @DisplayName("Testing delete a user")
    void test_delete() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/user/delete/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Testing get all users")
    void test_getAll() throws Exception {
        //Arrange
        List<UserDto> dto = List.of(getUserDto());
        when(userService.getAllUsers()).thenReturn(dto);

        mockMvc.perform(get("/api/user/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].phoneNumber", is(dto.get(0).getPhoneNumber())))
                .andExpect(jsonPath("$[0].password", is(dto.get(0).getPassword())))
                .andExpect(jsonPath("$[0].address", is(dto.get(0).getAddress())))
                .andExpect(jsonPath("$[0].fullName", is(dto.get(0).getFullName())))
                .andExpect(jsonPath("$[0].email", is(dto.get(0).getEmail())));
    }


    private UserDto getUserDto() {
        return UserDto.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .address(ADDRESS)
                .phoneNumber(PHONENUMBER)
                .fullName(FULLNAME)
                .id(ID)
                .build();
    }

}
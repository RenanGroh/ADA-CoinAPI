package com.dev.api.adacoinapi.controller;

import com.dev.api.adacoinapi.dto.UserDTO;
import com.dev.api.adacoinapi.model.User;
import com.dev.api.adacoinapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void createUserTest() throws Exception {
        User newUser = new User();
        newUser.setId(1L);
        newUser.setUsername("newUser");
        newUser.setPassword("password");

        when(userService.createUser(any(User.class))).thenReturn(newUser);

        mockMvc.perform(post("/api/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"newUser\", \"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("newUser"));
    }

    @Test
    @WithMockUser
    public void addFavoriteCurrencyTest() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("existingUser");

        UserDTO userDTO = new UserDTO(user);

        when(userService.addFavoriteCurrency(1L, 101L)).thenReturn(user);

        mockMvc.perform(post("/api/users/{userId}/favoriteCurrencies/{quoteId}", 1, 101))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("existingUser"));
    }

    @Test
    public void createUserWithInvalidDataTest() throws Exception {
        mockMvc.perform(post("/api/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"\", \"password\": \"\"}"))
                .andExpect(status().isBadRequest());
    }
}

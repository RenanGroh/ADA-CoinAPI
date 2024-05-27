package com.dev.api.adacoinapi.controller;

import com.dev.api.adacoinapi.model.User;
import com.dev.api.adacoinapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testCreateUserWithParams() {
        // Given
        String username = "testuser";
        String password = "password";
        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPassword(password);

        when(userService.createUser(username, password)).thenReturn(mockUser);

        // When
        ResponseEntity<User> response = userController.createUser(username, password);

        // Then
        assertEquals(ResponseEntity.ok(mockUser), response);
    }

    @Test
    void testCreateUserWithBody() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword("password");

        when(userService.createUser(any(User.class))).thenReturn(mockUser);

        // When
        ResponseEntity<User> response = userController.createUser(user);

        // Then
        assertEquals(ResponseEntity.ok(mockUser), response);
    }
}

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testCreateUserWithParams() throws Exception {
        User mockUser = new User(1L, "user", "pass");
        when(userService.createUser("user", "pass")).thenReturn(mockUser);

        mockMvc.perform(post("/api/users")
                .param("username", "user")
                .param("password", "pass"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user"));
    }

    @Test
    void testAddFavoriteCurrency() throws Exception {
        User mockUser = new User(1L, "user", "pass");
        when(userService.addFavoriteCurrency(1L, 1L)).thenReturn(mockUser);

        mockMvc.perform(post("/api/users/{userId}/favoriteCurrencies/{quoteId}", 1, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user"));
    }
}


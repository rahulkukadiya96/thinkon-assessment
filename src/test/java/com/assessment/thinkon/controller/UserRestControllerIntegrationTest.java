package com.assessment.thinkon.controller;

import com.assessment.thinkon.pojo.UserData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRestControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testFetchUsers() throws Exception {
        // Arrange

        // Act
        ResultActions result = mockMvc.perform(get("/users")
                .contentType(APPLICATION_JSON));

        // Assert
        result.andExpect(status().isOk());
    }
    public UserData getValidUserData() {
        UserData user = new UserData();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPhoneNumber("8073572500");
        user.setFirstName("Test");
        user.setLastName("Test");
        return user;
    }
}

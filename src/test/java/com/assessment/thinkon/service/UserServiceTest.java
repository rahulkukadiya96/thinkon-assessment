package com.assessment.thinkon.service;

import com.assessment.thinkon.exception.InvalidDataException;
import com.assessment.thinkon.model.User;
import com.assessment.thinkon.pojo.ResponseBody;
import com.assessment.thinkon.pojo.UserData;
import com.assessment.thinkon.repository.UserRepository;
import com.assessment.thinkon.utils.ResponseUtils;
import com.assessment.thinkon.utils.UserValidationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static com.assessment.thinkon.constants.Messages.REQUEST_FAILED;
import static com.assessment.thinkon.constants.Messages.USER_CREATED_SUCCESSFULLY;
import static com.assessment.thinkon.constants.Messages.USER_EXISTED_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ResponseUtils responseUtils;

    @Mock
    private UserValidationUtils userValidationUtils;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void shouldReturnCreatedWhenUserIsSuccessfullyCreated() throws InvalidDataException {
        // Prepare mock data
        UserData userData = getValidUserData();

        // Mocking repository and utils behavior
        doNothing().when(userValidationUtils).validateUserRequest(userData);
        when(userRepository.existsByUserNameAndDeleteFlag("testuser", 0)).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(getValidUser());
        when(responseUtils.buildResponseEntity(USER_CREATED_SUCCESSFULLY, HttpStatus.CREATED))
                .thenReturn(new ResponseEntity<>(new ResponseBody<>(USER_CREATED_SUCCESSFULLY, Collections.emptyList()), HttpStatus.CREATED));

        // Call the service method
        ResponseEntity<ResponseBody> response = userService.createUser(userData);

        // Verify the interactions and assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(USER_CREATED_SUCCESSFULLY, response.getBody().getMessage());
    }

    @Test
    public void shouldReturnConflictWhenUserAlreadyExistsInCreateUser() throws InvalidDataException {
        // Prepare mock data
        UserData userData = getValidUserData();

        // Mocking repository and utils behavior
        doNothing().when(userValidationUtils).validateUserRequest(userData);
        when(userRepository.existsByUserNameAndDeleteFlag("testuser", 0)).thenReturn(true);
        when(responseUtils.buildResponseEntity(USER_EXISTED_ERROR_MESSAGE, CONFLICT))
                .thenReturn(new ResponseEntity<>(new ResponseBody<>(USER_EXISTED_ERROR_MESSAGE, Collections.emptyList()), CONFLICT));

        // Call the service method
        ResponseEntity<ResponseBody> response = userService.createUser(userData);

        // Verify the interactions and assert
        assertEquals(CONFLICT, response.getStatusCode());
        assertEquals(USER_EXISTED_ERROR_MESSAGE, response.getBody().getMessage());
    }

    @Test
    public void shouldReturnServerErrorWhenCreateUserFails() throws InvalidDataException {
        // Prepare mock data
        UserData userData = getValidUserData();

        // Mocking repository and utils behavior
        doNothing().when(userValidationUtils).validateUserRequest(userData);
        when(userRepository.existsByUserNameAndDeleteFlag("testuser", 0)).thenReturn(false);
        User user = getValidUser();
        user.setId(0);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(responseUtils.buildResponseEntity(REQUEST_FAILED, INTERNAL_SERVER_ERROR))
                .thenReturn(new ResponseEntity<>(new ResponseBody<>(REQUEST_FAILED, Collections.emptyList()), INTERNAL_SERVER_ERROR));

        // Call the service method
        ResponseEntity<ResponseBody> response = userService.createUser(userData);

        // Verify the interactions and assert
        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(REQUEST_FAILED, response.getBody().getMessage());
    }

    public User getValidUser() {
        User user = new User();
        user.setId(1L);
        user.setUserName("testuser");
        user.setEmail("test@example.com");
        user.setPhoneNumber("8073572500");
        user.setDeleteFlag(0);
        user.setFirstName("Test");
        user.setLastName("Test");
        return user;
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

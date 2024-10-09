package com.assessment.thinkon.controller;

import com.assessment.thinkon.exception.InvalidDataException;
import com.assessment.thinkon.pojo.ResponseBody;
import com.assessment.thinkon.pojo.UserData;
import com.assessment.thinkon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * This is controller class for the user related apis
 */
@RestController
@RequestMapping("/users")
public class UserRestController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody> createUser(@RequestBody UserData userData) throws InvalidDataException {
        return userService.createUser(userData);
    }

    @GetMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody> fetchUsers() {
        return userService.fetchUserList();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseBody> fetchUserById(@PathVariable int id) {
        return userService.fetchUserById(id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseBody> deleteUserById(@PathVariable int id) {
        return userService.deleteUserById(id);
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody> deleteUserById(@PathVariable int id, @RequestBody UserData userData) throws InvalidDataException {
        return userService.updateUser(id, userData);
    }
}

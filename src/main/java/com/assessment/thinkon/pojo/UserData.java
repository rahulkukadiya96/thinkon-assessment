package com.assessment.thinkon.pojo;

import com.assessment.thinkon.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is DTO class for the User
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public User toUser() {
        User user = new User();
        user.setId(this.id);
        user.setUserName(this.username);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        user.setPhoneNumber(this.phoneNumber);
        return user;
    }
}

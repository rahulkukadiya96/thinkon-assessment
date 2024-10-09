package com.assessment.thinkon.projection;

import com.assessment.thinkon.pojo.UserData;

/**
 * This is projection interface for the user
 */
public interface UserProjection {
    int getId();

    String getFirstName();

    String getLastName();

    String getEmail();

    String getPhoneNumber();

    String getUserName();

    default UserData toUserData() {
        UserData userData = new UserData();
        userData.setId(getId());
        userData.setFirstName(getFirstName());
        userData.setLastName(getLastName());
        userData.setEmail(getEmail());
        userData.setPhoneNumber(getPhoneNumber());
        userData.setUsername(getUserName());
        return userData;
    }
}

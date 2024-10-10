package com.assessment.thinkon.service;

import com.assessment.thinkon.exception.InvalidDataException;
import com.assessment.thinkon.model.User;
import com.assessment.thinkon.pojo.ResponseBody;
import com.assessment.thinkon.pojo.UserData;
import com.assessment.thinkon.projection.UserProjection;
import com.assessment.thinkon.repository.UserRepository;
import com.assessment.thinkon.utils.ResponseUtils;
import com.assessment.thinkon.utils.UserValidationUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.assessment.thinkon.constants.AppConstants.NO;
import static com.assessment.thinkon.constants.AppConstants.YES;
import static com.assessment.thinkon.constants.Messages.REQUEST_FAILED;
import static com.assessment.thinkon.constants.Messages.SUCCESS;
import static com.assessment.thinkon.constants.Messages.USER_CREATED_SUCCESSFULLY;
import static com.assessment.thinkon.constants.Messages.USER_DELETED_SUCCESSFULLY;
import static com.assessment.thinkon.constants.Messages.USER_EXISTED_ERROR_MESSAGE;
import static com.assessment.thinkon.constants.Messages.USER_NOT_FOUND;
import static com.assessment.thinkon.constants.Messages.USER_UPDATED_SUCCESSFULLY;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

/**
 * This is service class to perform business logic on user
 */
@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResponseUtils responseUtils;

    @Autowired
    private UserValidationUtils userValidationUtils;

    /**
     * This API is used to create new user.
     * It checks that email is existed into the database and return error message if email is already existed.
     *
     * @param userData user request data
     * @return response with message
     */
    public ResponseEntity<ResponseBody> createUser(UserData userData) throws InvalidDataException {
        userValidationUtils.validateUserRequest(userData);

        if (userRepository.existsByUserNameAndDeleteFlag(userData.getUsername(), NO)) {
            return responseUtils.buildResponseEntity(USER_EXISTED_ERROR_MESSAGE, CONFLICT);
        }

        User user = userData.toUser();
        userRepository.save(user);
        if (user.getId() > 0) {
            return responseUtils.buildResponseEntity(USER_CREATED_SUCCESSFULLY, CREATED);
        }
        return responseUtils.buildResponseEntity(REQUEST_FAILED, INTERNAL_SERVER_ERROR);
    }

    /**
     * This API is used to fetch user list
     *
     * @return not deleted user list
     */
    public ResponseEntity<ResponseBody> fetchUserList() {
        List<UserData> userDataList = userRepository.findByDeleteFlag(NO).stream().map(UserProjection::toUserData).toList();
        if (userDataList.isEmpty()) {
            return responseUtils.buildResponseEntity(USER_NOT_FOUND, NOT_FOUND);
        }
        return responseUtils.buildResponseEntity(SUCCESS, OK, userDataList);
    }

    /**
     * This API is used to fetch user list
     *
     * @return not deleted user list
     */
    public ResponseEntity<ResponseBody> fetchUserById(long id) {
        Optional<UserProjection> userDataOptional = userRepository.findByIdAndDeleteFlag(id, NO, UserProjection.class);
        if (userDataOptional.isEmpty()) {
            return responseUtils.buildResponseEntity(USER_NOT_FOUND, NOT_FOUND);
        }
        return responseUtils.buildResponseEntity(SUCCESS, OK, userDataOptional.map(UserProjection::toUserData));
    }

    /**
     * This API is used to delete user. It performs soft delete operation.
     *
     * @param id id of user
     * @return message
     */
    public ResponseEntity<ResponseBody> deleteUserById(long id) {
        Optional<User> userOptional = userRepository.findByIdAndDeleteFlag(id, NO, User.class);
        if (userOptional.isEmpty()) {
            return responseUtils.buildResponseEntity(USER_NOT_FOUND, NOT_FOUND);
        }
        User user = userOptional.get();
        user.setDeleteFlag(YES);
        userRepository.save(user);

        return responseUtils.buildResponseEntity(USER_DELETED_SUCCESSFULLY, OK);
    }

    public ResponseEntity<ResponseBody> updateUser(long id, UserData userData) throws InvalidDataException {
        // Validate user data
        userValidationUtils.validateUserRequest(userData);

        // Check if user exists
        Optional<User> userOptional = userRepository.findByIdAndDeleteFlag(id, NO, User.class);
        if (userOptional.isEmpty()) {
            return responseUtils.buildResponseEntity(USER_NOT_FOUND, NOT_FOUND);
        }

        // Check if a user with the new username already exists
        if (userRepository.existsByUserNameAndIdNotAndDeleteFlag(userData.getUsername(), id, NO)) {
            return responseUtils.buildResponseEntity(USER_EXISTED_ERROR_MESSAGE, CONFLICT);
        }

        // Update the user data
        User user = userData.toUser();
        user.setUserName(userData.getUsername());
        user.setEmail(userData.getEmail());
        user.setPhoneNumber(userData.getPhoneNumber());
        user.setLastName(userData.getLastName());
        user.setFirstName(userData.getFirstName());
        userRepository.save(user);

        // Return success response
        return responseUtils.buildResponseEntity(USER_UPDATED_SUCCESSFULLY, OK);
    }
}

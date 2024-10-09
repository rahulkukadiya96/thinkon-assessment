package com.assessment.thinkon.utils;

import com.assessment.thinkon.exception.InvalidDataException;
import com.assessment.thinkon.pojo.UserData;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Optional;

import static com.assessment.thinkon.Constants.Messages.EMAIL_NOT_VALID;
import static com.assessment.thinkon.Constants.Messages.FIRST_NAME_NOT_VALID;
import static com.assessment.thinkon.Constants.Messages.LAST_NAME_NOT_VALID;
import static com.assessment.thinkon.Constants.Messages.PHONE_NUMBER_NOT_VALID;
import static com.assessment.thinkon.Constants.Messages.USER_NAME_NOT_VALID;
import static java.util.Optional.ofNullable;

@Component
public class UserValidationUtils {
    private static final int DEFAULT_TEXT_MAX_CHAR = 50;
    private static final int PHONE_MAX_CHAR = 15;

    public static final String REGEX_PHONE_NUMBER = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$";

    public static final String REGEX_EMAIL = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";

    public boolean isEmailValid(Optional<String> email) {
        return email
                .filter(num -> genericStringValidation(email, DEFAULT_TEXT_MAX_CHAR))
                .map(num -> num.matches(REGEX_EMAIL))
                .orElse(false);
    }

    public boolean isFirstNameValid(Optional<String> firstName) {
        return genericStringValidation(firstName, DEFAULT_TEXT_MAX_CHAR);
    }

    public boolean isLastNameValid(Optional<String> lastName) {
        return genericStringValidation(lastName, DEFAULT_TEXT_MAX_CHAR);
    }

    public boolean isUserNameValid(Optional<String> userName) {
        return genericStringValidation(userName, DEFAULT_TEXT_MAX_CHAR);
    }

    public boolean isPhoneNumberValid(Optional<String> phoneNumber) {
        return phoneNumber
                .filter(num -> genericStringValidation(phoneNumber, PHONE_MAX_CHAR))
                .map(num -> num.matches(REGEX_PHONE_NUMBER))
                .orElse(false);
    }

    public boolean isEmptyOrNull(Optional<String> value) {
        return value.map(String::isEmpty).orElse(true);
    }

    public boolean isMaxCharacter(Optional<String> value, int maxCharacter) {
        return value.map(String::length).orElse(0) > maxCharacter;
    }

    public boolean genericStringValidation(Optional<String> value, int maxCharacters) {
        return !isEmptyOrNull(value) || !isMaxCharacter(value, maxCharacters);
    }

    /**
     * This API is validated the user data and throw an exception if is not valid
     *
     * @param userData user data
     */
    public void validateUserRequest(UserData userData) throws InvalidDataException {
        LinkedList<String> errors = new LinkedList<>();
        if (!isEmailValid(ofNullable(userData.getEmail()))) {
            errors.add(EMAIL_NOT_VALID);
        }
        if (!isFirstNameValid(ofNullable(userData.getFirstName()))) {
            errors.add(FIRST_NAME_NOT_VALID);
        }

        if (!isUserNameValid(ofNullable(userData.getLastName()))) {
            errors.add(USER_NAME_NOT_VALID);
        }

        if (!isLastNameValid(ofNullable(userData.getUsername()))) {
            errors.add(LAST_NAME_NOT_VALID);
        }

        if (!isPhoneNumberValid(ofNullable(userData.getPhoneNumber()))) {
            errors.add(PHONE_NUMBER_NOT_VALID);
        }

        if (!errors.isEmpty()) {
            throw new InvalidDataException(errors);
        }
    }
}

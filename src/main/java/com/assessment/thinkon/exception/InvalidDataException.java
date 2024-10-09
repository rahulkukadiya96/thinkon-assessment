package com.assessment.thinkon.exception;

import lombok.Data;

import java.io.Serial;
import java.util.List;

/**
 * Exception class for custom validation on user request
 */
@Data
public class InvalidDataException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;
    private final List<String> errors;

    public InvalidDataException(List<String> errors) {
        this.errors = errors;
    }
}

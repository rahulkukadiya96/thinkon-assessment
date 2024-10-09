package com.assessment.thinkon.advice;

import com.assessment.thinkon.exception.InvalidDataException;
import com.assessment.thinkon.pojo.ResponseBody;
import com.assessment.thinkon.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.assessment.thinkon.Constants.Messages.REQUEST_FAILED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private ResponseUtils responseUtils;

    /**
     * Handle invalid data exception globally
     *
     * @return Response with error message list
     */
    @ExceptionHandler(value = InvalidDataException.class)
    protected ResponseEntity<ResponseBody> handleInvalidDataException(InvalidDataException invalidDataException) {
        return responseUtils.buildResponseEntity(REQUEST_FAILED, BAD_REQUEST, invalidDataException.getErrors());
    }
}

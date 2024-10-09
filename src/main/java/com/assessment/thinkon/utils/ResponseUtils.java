package com.assessment.thinkon.utils;

import com.assessment.thinkon.pojo.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * This is response related utility class
 */
@Component
public class ResponseUtils {

    /**
     * This helper message build the response
     * @param message :- the message
     * @return response object
     */

    public ResponseBody buildResponse(String message) {
        return new ResponseBody<>(message, emptyList());
    }

    public <T> ResponseBody<T> buildResponse(String message, T data) {
        return new ResponseBody<>(message, singletonList(data));
    }

    public <T> ResponseBody<T> buildResponse(String message, List<T> data) {
        return new ResponseBody<>(message, data);
    }


    /**
     * This API is used to build response.
     * @param message message
     * @param status status code
     * @return response entity object
     */
    public ResponseEntity<ResponseBody> buildResponseEntity(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(buildResponse(message));
    }

    public <T> ResponseEntity<ResponseBody> buildResponseEntity(String message, HttpStatus code, T data) {
        return ResponseEntity.status(code).body(buildResponse(message, data));
    }

    public <T> ResponseEntity<ResponseBody> buildResponseEntity(String message, HttpStatus code, List<T> data) {
        return ResponseEntity.status(code).body(buildResponse(message, data));
    }
}

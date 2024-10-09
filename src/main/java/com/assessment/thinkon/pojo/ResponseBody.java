package com.assessment.thinkon.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

/**
 * This is Common Response Class
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBody <T> {
    private String message;

    @JsonInclude(NON_EMPTY)
    private List<T> data;
}

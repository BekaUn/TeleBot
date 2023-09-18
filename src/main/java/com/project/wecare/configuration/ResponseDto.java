package com.project.wecare.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {
    private boolean success;

    private String message;
    /*
     * 0   - it is ok
     *-1  - not found
     *-2  - validation error
     * -3 - database error
     * */

    private int code;

    private T data;

    private List<ErrorDto> errors;

}

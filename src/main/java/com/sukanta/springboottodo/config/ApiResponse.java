package com.sukanta.springboottodo.config;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
public class ApiResponse<T> {

    @JsonProperty(required = true)
    private boolean error;
    @JsonProperty(required = true)
    private String message;
    private String access_token;

    private T data;

}

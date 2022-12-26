package com.cydeo.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper {

    private boolean success;
    private String message;
    private Integer code;
    private Object data;

    public ResponseWrapper(String message, Object data, HttpStatus status) {
        this.success = true;
        this.message = message;
        this.code = status.value();
        this.data = data;
    }

    public ResponseWrapper(String message,HttpStatus status) {
        this.success = true;
        this.message = message;
        this.code = status.value();
    }
}

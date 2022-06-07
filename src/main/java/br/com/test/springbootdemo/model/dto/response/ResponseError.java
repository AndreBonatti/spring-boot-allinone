package br.com.test.springbootdemo.model.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ResponseError {

    private HttpStatus status;
    private String message;
    private String detail;
}

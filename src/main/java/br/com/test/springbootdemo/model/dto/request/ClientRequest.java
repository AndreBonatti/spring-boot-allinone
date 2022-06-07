package br.com.test.springbootdemo.model.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ClientRequest {

    @ApiModelProperty(example = "Pedro")
    private String name;
    @ApiModelProperty(example = "2000-01-01")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;
    @ApiModelProperty(example = "rua bahia, 762")
    private String annotation;
}

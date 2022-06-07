package br.com.test.springbootdemo.config.interceptor;

import br.com.test.springbootdemo.model.dto.response.ResponseError;
import br.com.test.springbootdemo.model.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class HandlerExceptionInterceptor {

    @Autowired
    private ModelMapper modelMapper;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> handleExceptions(Exception e) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        var response = ResponseError.builder()
                                                .status(status)
                                                .message(e.getMessage())
                                                .detail(e.getCause().toString())
                                                .build();

        return new ResponseEntity<>((response),status);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseError> handleBusinessException(BusinessException e) {

        var response = ResponseError.builder()
                .status(e.getStatus())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>((response),e.getStatus());
    }

    @ExceptionHandler(ListenerExecutionFailedException.class)
    public void handleBusinessException(ListenerExecutionFailedException e) {
        log.error("Kafka error1");
        log.error(e.getMessage(), e.getCause());
    }


    @ExceptionHandler(RuntimeException.class)
    public void handleBusinessException(RuntimeException e) {
        log.error("Kafka error2");
        log.error(e.getMessage(), e.getCause());
    }
}

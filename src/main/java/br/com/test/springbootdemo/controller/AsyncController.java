package br.com.test.springbootdemo.controller;

import br.com.test.springbootdemo.service.AsyncPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/async-pool")
public class AsyncController {

    @Autowired
    private AsyncPoolService asyncPoolService;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "/send")
    public ResponseEntity<Void> send(@RequestParam(defaultValue = "Mensagem assíncrono") String message){
        asyncPoolService.process(message);
        return ResponseEntity.accepted().build();
    }
}

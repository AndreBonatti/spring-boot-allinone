package br.com.test.springbootdemo.controller;

import br.com.test.springbootdemo.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/kafka")
public class KafkaController {

    @Autowired
    private KafkaService kafkaService;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "/send")
    public ResponseEntity<Void> send(@RequestParam(defaultValue = "Mensagem teste Kafka") String message){
        kafkaService.send(message);
        return ResponseEntity.accepted().build();
    }
}

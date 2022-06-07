package br.com.test.springbootdemo.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AsyncPoolService {

    @SneakyThrows
    @Async
    public void process(String message){
        // processo assincrono
        log.info("Inicio: {}", message);
        Thread.sleep(1000L); // perde 1 segundo
        log.info("Fim: {}", message);
    }
}

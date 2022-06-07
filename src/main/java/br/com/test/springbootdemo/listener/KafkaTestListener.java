package br.com.test.springbootdemo.listener;

import br.com.test.springbootdemo.model.dto.kafka.PayloadKafka;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class KafkaTestListener {

    @Value("app.kafka.test")
    boolean teste;

    @KafkaListener(topics = "${topic.name.consumer}", containerFactory = "customListenerContainerFactory")
    public void consume(List<ConsumerRecord<String, PayloadKafka>> listPayload) {

        log.info("Batch size: {}", listPayload.size());

        if(teste) throw new NoSuchElementException("FORÇAR EXECECAO");

        listPayload.forEach( consumerRecord -> {
            log.info("Topic: {}", consumerRecord.topic());
            log.info("key: {}", consumerRecord.key());
            log.info("Offset: {}", consumerRecord.offset());
            log.info("Headers: {}", consumerRecord.headers());
            log.info("Partion: {}", consumerRecord.partition());
            log.info("Order: {}", consumerRecord.value());
        });
    }

}

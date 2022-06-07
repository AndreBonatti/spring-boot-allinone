package br.com.test.springbootdemo.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;
import org.springframework.util.backoff.FixedBackOff;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "custom.kafka.consumer")
public class KafkaConsumerConfig {

    private String bootstrapServers;
    private String groupId;
    private String autoOffsetReset;
    private Integer maxPollRecords;


    @Value("${topic.name.consumer}")
    private String topic;


    private ConsumerFactory<String, Object> consumerFactory(String paramGroupId) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, paramGroupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> customListenerContainerFactory(KafkaTemplate<String, Object> template) {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(groupId));
        factory.setBatchListener(true);
        factory.setCommonErrorHandler(defaultErrorHandler(template));
        return factory;
    }

    public DefaultErrorHandler defaultErrorHandler(KafkaTemplate<String, Object> template){

        var recover = new DeadLetterPublishingRecoverer(template,
                (consumerRecord, exception) -> new TopicPartition(consumerRecord.topic() + ".DLT", 0));

        var exponentialBackOff = new ExponentialBackOffWithMaxRetries(1);
        exponentialBackOff.setInitialInterval(500L);
        exponentialBackOff.setMultiplier(1.5);
        exponentialBackOff.setMaxInterval(2_000L);

        return new DefaultErrorHandler(recover, exponentialBackOff);
    }

    private DefaultErrorHandler defaultErrorHandlerBasic(){
        return new DefaultErrorHandler((consumerRecord, e) ->{
            //Redirecionar para uma banco de dados ou colocar numa fila/tópico
            log.info("");
            log.info(consumerRecord.toString());
            log.error(e.getMessage());
        },new FixedBackOff(10_000L, 2L));
    }

}

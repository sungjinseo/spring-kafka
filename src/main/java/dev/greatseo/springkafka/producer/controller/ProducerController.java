package dev.greatseo.springkafka.producer.controller;

import dev.greatseo.springkafka.producer.dto.EventDto;
import dev.greatseo.springkafka.producer.service.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin("*")
@RestController
public class ProducerController {
    @Value("${spring.kafka.topic.test}")
    private String topic;

    private final Logger logger = LoggerFactory.getLogger(ProducerController.class);

    @Autowired
    KafkaTemplate kafkaTemplate;

    @GetMapping("/api/test")
    public void setKafkaRequest(@RequestParam String title, @RequestParam String content) {
        EventDto event = new EventDto();
        event.setTitle(title);
        event.setContent(content);

        kafkaTemplate.send(topic, event).addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                logger.error(ex.getMestsage(), ex);
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info(result.toString());
            }
        });
    }
}
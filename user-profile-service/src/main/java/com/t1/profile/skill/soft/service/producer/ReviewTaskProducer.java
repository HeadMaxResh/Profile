package com.t1.profile.skill.soft.service.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReviewTaskProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public ReviewTaskProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTaskCompletedMessage(Integer raterUserId, Integer ratedUserId) {
        String message = String.format(
                "%d:%d",
                raterUserId,
                ratedUserId
        );
        kafkaTemplate.send("task_updates", message);
    }

}

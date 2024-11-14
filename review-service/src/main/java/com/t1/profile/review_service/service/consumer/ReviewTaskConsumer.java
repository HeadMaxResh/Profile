package com.t1.profile.review_service.service.consumer;

import com.t1.profile.review_service.service.ReviewService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ReviewTaskConsumer {

    @Autowired
    private ReviewService reviewService;

    @KafkaListener(topics = "task_updates", groupId = "review_group")
    public void listen(ConsumerRecord<String, String> record) {
        String message = record.value();
        System.out.println("Получено сообщение: " + message);
        processTaskCompletion(message);
    }


    private void processTaskCompletion(String message) {
        try {
            String[] parts = message.split(":");
            if (parts.length == 2) {
                String raterUserIdStr = parts[0].trim();
                String ratedUserIdStr = parts[1].trim();
                Integer raterUserId = Integer.parseInt(raterUserIdStr);
                Integer ratedUserId = Integer.parseInt(ratedUserIdStr);

                reviewService.markTaskAsCompleted(raterUserId, ratedUserId);
            } else {
                System.err.println("Неверный формат сообщения: " + message);
            }
        } catch (Exception e) {
            System.err.println("Ошибка при обработке сообщения: " + message);
            e.printStackTrace();
        }
    }

}

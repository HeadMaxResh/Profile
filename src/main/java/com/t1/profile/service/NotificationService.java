package com.t1.profile.service;

import com.t1.profile.model.ReviewCycle;
import com.t1.profile.model.ReviewTask;
import com.t1.profile.model.User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class NotificationService {

    // Удаляем зависимость от EmailService
    // @Autowired
    // private EmailService emailService;

    // Уведомление о начале цикла ревью
    public void notifyReviewCycleStarted(ReviewCycle reviewCycle) {
        // Отправьте уведомления всем участникам ревью
        Set<User> participants = getAllParticipants(reviewCycle);

        for (User user : participants) {
            // Вместо отправки email можно добавить логирование или оставить пустым
            // Например:
            System.out.println("Уведомление: Цикл ревью '" + reviewCycle.getName() + "' начался для пользователя " + user.getEmail());
        }
    }

    // Уведомление о завершении цикла ревью
    public void notifyReviewCycleCompleted(ReviewCycle reviewCycle) {
        Set<User> participants = getAllParticipants(reviewCycle);

        for (User user : participants) {
            // Логирование или пустое тело метода
            System.out.println("Уведомление: Цикл ревью '" + reviewCycle.getName() + "' завершен для пользователя " + user.getEmail());
        }
    }

    // Уведомление ревьюера о назначении задания
    public void notifyReviewerAssigned(ReviewTask reviewTask) {
        User reviewer = reviewTask.getReviewer();
        User reviewee = reviewTask.getReviewee();

        // Логирование или пустое тело метода
        System.out.println("Уведомление: Пользователю " + reviewer.getEmail() + " назначено ревью пользователя " + reviewee.getEmail());
    }

    private Set<User> getAllParticipants(ReviewCycle reviewCycle) {
        Set<User> participants = new HashSet<>();
        for (ReviewTask task : reviewCycle.getReviewTasks()) {
            participants.add(task.getReviewer());
            participants.add(task.getReviewee());
        }
        return participants;
    }
}

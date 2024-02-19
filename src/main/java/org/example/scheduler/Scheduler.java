package org.example.scheduler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.example.repository.NotificationTaskRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class Scheduler {
    private final NotificationTaskRepository notificationTaskRepository;
    private final TelegramBot telegramBot;

    public Scheduler(NotificationTaskRepository notificationTaskRepository, TelegramBot telegramBot) {
        this.notificationTaskRepository = notificationTaskRepository;
        this.telegramBot = telegramBot;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void sendNotificationTasks() {
        notificationTaskRepository.findAllByNotificationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                .forEach(notificationTask -> {
                    SendMessage message = new SendMessage(notificationTask.getChatId(), String.format("Напоминаю, как и обещал: %s", notificationTask.getNotificationText()));
                    telegramBot.execute(message);
                });
    }
}


package org.example.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.example.model.NotificationTask;
import org.example.repository.NotificationTaskRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    private final NotificationTaskRepository notificationTaskRepository;

    public TelegramBotUpdatesListener(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) throws NullPointerException {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            String messageText = "Привет! Хочешь поставить напоминание!? Жду от тебя время и дату и о чем ты хочешь себе напомнить.";
            Long chatId = update.message().chat().id();
            if (update.message().text() != null &&update.message().text().equals("/start")) {
                try {
                    SendMessage message = new SendMessage(chatId, messageText);
                    telegramBot.execute(message);
                } catch (NullPointerException e) {
                    System.out.println("Сообщение не может быть пустым!");
                } finally {
                    System.out.println("Проверка завершена");
                }
            }

            try {
                Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
                if (update.message().text() != null) {
                    Matcher matcher = pattern.matcher(update.message().text());
                    String date = null;
                    String item = null;
                    if (matcher.matches()) {
                        date = matcher.group(1);
                        item = matcher.group(3);
                        System.out.println(date);
                        System.out.println(item);
                        logger.info("Date: {}, item: {}", date, item);
                    }

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                    if (date != null) {
                        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
                        notificationTaskRepository.save(new NotificationTask(chatId, item, dateTime));
                        SendMessage message = new SendMessage(chatId, "Я запомнил!");
                        telegramBot.execute(message);
                    }
                }

            } catch (NullPointerException exception) {
                System.out.println("Сообщение не может быть пустым!");
            } finally {
                System.out.println("Проверка завершена");
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}


package org.example.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class NotificationTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;
    private Long chatId;
    private LocalDateTime notificationDate;
    private String notificationText;

    public NotificationTask(Long chatId, String notificationText, LocalDateTime notificationDate) {
        this.chatId = chatId;
        this.notificationDate = notificationDate;
        this.notificationText = notificationText;
    }

    public NotificationTask() {
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public LocalDateTime getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(LocalDateTime notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return Objects.equals(getNotificationId(), that.getNotificationId()) && Objects.equals(getChatId(), that.getChatId()) && Objects.equals(getNotificationDate(), that.getNotificationDate()) && Objects.equals(getNotificationText(), that.getNotificationText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNotificationId(), getChatId(), getNotificationDate(), getNotificationText());
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "notificationId=" + notificationId +
                ", chatId=" + chatId +
                ", notificationDate=" + notificationDate +
                ", notificationText='" + notificationText + '\'' +
                '}';
    }
}

package example.dto;

public class ReminderNotificationDto {
    private Integer userId;
    private String notificationType;

    public ReminderNotificationDto(Integer userId, String notificationType) {
        this.userId = userId;
        this.notificationType = notificationType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }
}
package example.service.impl;

import example.domen.ZakazaniTermin;
import example.dto.ReminderNotificationDto;
import example.helper.MessageHelper;
import example.repository.ZakazaniTerminRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

@Component
public class NotificationSheduler {

    private final JmsTemplate jmsTemplate;
    private final String destination;
    private final ZakazaniTerminRepository zakazaniTerminRepository;
    private final MessageHelper messageHelper;

    public NotificationSheduler(JmsTemplate jmsTemplate, @Value("${destination.reminderNotification}") String destination, ZakazaniTerminRepository zakazaniTerminRepository, MessageHelper messageHelper){
        this.destination = destination;
        this.jmsTemplate = jmsTemplate;
        this.zakazaniTerminRepository = zakazaniTerminRepository;
        this.messageHelper = messageHelper;
    }

    @Scheduled(fixedDelay = 86400000, initialDelay = 2000)
    void sheduleTask()
    {
        for(ZakazaniTermin zakazaniTermin: zakazaniTerminRepository.findAll()){
            Date sqlDate = new java.sql.Date(System.currentTimeMillis());
            long diff = Math.abs(zakazaniTermin.getTerminTreninga().getDatum().getTime()- sqlDate.getTime());
            long dayDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
            System.out.println(dayDiff + " id: " + zakazaniTermin.getId());
            if(dayDiff==1){
                ReminderNotificationDto reminderNotificationDto = new ReminderNotificationDto(zakazaniTermin.getKlijentId(),"REMINDER_EMAIL");
                jmsTemplate.convertAndSend(destination,messageHelper.createTextMessage(reminderNotificationDto));
            }
        }
    }
}

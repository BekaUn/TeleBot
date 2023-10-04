package com.project.wecare.configuration;

import com.project.wecare.controller.TelegramBotController;
import com.project.wecare.repository.UserRepository;
import com.project.wecare.service.Admin;
import com.project.wecare.service.Button;
import com.project.wecare.service.Ru;
import com.project.wecare.service.Uz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.meta.generics.WebhookBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Component
public class BotConfig {
    /*  @Autowired
      TelegramBotController bot;*/
    @Autowired
    UserRepository userRepository;
@Autowired
Uz uz;
@Autowired
Ru ru;
@Autowired
Admin admin;
@Autowired
Button button;
    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        TelegramBotController botController = new TelegramBotController(ru, uz, admin, button, userRepository);
        try {
           // telegramBotsApi.registerBot(new WebhookBot(),new SetWebhook());
            telegramBotsApi.registerBot(botController);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }
}

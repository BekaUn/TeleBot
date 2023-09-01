package com.project.wecare.controller;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class SendPhotos {
    public SendPhoto photo(Update update) {
            Message message = update.getMessage();
            String chatId = message.getChatId().toString();
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setChatId(chatId);
                sendPhoto.setPhoto(new InputFile(new java.io.File("/home/sardor/Backend/Project/TeleBot/src/main/resources/img.png")));
               return sendPhoto;
            }



}

package com.project.wecare.service;


import com.project.wecare.controller.TelegramBotController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class Ru {
    @Lazy
    @Autowired
    TelegramBotController telegramBotController;

    public String conditionRu() {
        return "Чтобы запросить рассрочку нажмите «ПРОДОЛЖИТЬ». Внимание! Нажимая «ПРОДОЛЖИТЬ», вы даете согласие на обработку своих персональных данных";
    }

    public String conditionRu2() {
        return "Для оформления заявки необходимо предоставить некоторые документы. Ознакомьтесь с основными причинами получения отказа в рассрочке: \n" +
                "• Возрастное ограничение: от 18 до 60 лет. \n" +
                "• Наличие отрицательной кредитной истории \n" +
                "• Кредитная задолженность \n" +
                "• Долг в БПИ (MIB) \n" +
                "• За последние 4 месяца поступления были низкими";
    }

    public void ru(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(conditionRu());
        sendMessage.setChatId(message.getChatId());
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        KeyboardRow first = new KeyboardRow();
        KeyboardRow second = new KeyboardRow();
        KeyboardButton dale = new KeyboardButton();
        KeyboardButton backButton = new KeyboardButton();
        List<KeyboardRow> rowList = new ArrayList<>();
        dale.setText("▶\uFE0F Дальше.");
        backButton.setText("◀️ Назад");
        first.add(dale);
        second.add(backButton);
        rowList.add(first);
        rowList.add(second);
        replyKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        try {
            telegramBotController.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void newStepRu(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(conditionRu2());
        sendMessage.setChatId(message.getChatId());
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        KeyboardRow first = new KeyboardRow();
        KeyboardRow second = new KeyboardRow();
        KeyboardButton dale = new KeyboardButton();
        KeyboardButton backButton = new KeyboardButton();
        List<KeyboardRow> rowList = new ArrayList<>();
        dale.setText("▶\uFE0F Дальше");
        backButton.setText("◀️ Назад");
        first.add(dale);
        second.add(backButton);
        rowList.add(first);
        rowList.add(second);
        replyKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        try {
            telegramBotController.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

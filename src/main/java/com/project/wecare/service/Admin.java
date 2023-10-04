package com.project.wecare.service;

import com.project.wecare.controller.TelegramBotController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class Admin {
    @Lazy
    @Autowired
    TelegramBotController telegramBotController;

    public void yesNoAdmin(long chatId, String text) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
        var payment = new InlineKeyboardButton();
        payment.setText("Переотправить переднюю часть паспорта");
        payment.setCallbackData(telegramBotController.getPASSPORT_BUTTON());
        var back = new InlineKeyboardButton();
        back.setText("Потвердить паспорт");
        back.setCallbackData(telegramBotController.getNO_BUTTON());
        rowInLine.add(payment);
        rowInLine1.add(back);
        rowsInLine.add(rowInLine);
        rowsInLine.add(rowInLine1);
        markupInLine.setKeyboard(rowsInLine);
        sendMessage.setReplyMarkup(markupInLine);
        telegramBotController.execute(sendMessage);
    }
}

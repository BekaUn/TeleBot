package com.project.wecare.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Button {
    private final Uz uz;
    private final Ru ru;

    public SendMessage button(Update update) {
        SendMessage returnMassage = new SendMessage();
        Message message = update.getMessage();
        String text = message.getText();

        if (text.equals("/start")) {
            returnMassage = buttonStart(message);
        }
        if (text.equals("\uD83C\uDDFA\uD83C\uDDFF Uz")) {
            returnMassage = uz.uz(message);
        }
        if (text.equals("\uD83C\uDDF7\uD83C\uDDFA Ru")) {
            returnMassage = ru.ru(message);
        }
        if (text.equals("◀️ Ortga qaytish") || text.equals("◀️ Назад")) {
            returnMassage = buttonStart(message);
        }
     /*   if (text.equals("▶\uFE0F Дальше")) {
            returnMassage = ru.step4(message);
        }
        if (text.equals("▶\uFE0F Дале")) {
            returnMassage = ru.step5(message);
        }*/
        if (text.equals("▶\uFE0F Keyingisi")) {
            returnMassage = uz.step4(update,message);
        }
        if (text.equals("▶\uFE0F Keyingisi.")) {
            returnMassage = uz.step5(message);
        }
        if (text.equals("◀️ Ortga qaytish..")) {
            returnMassage = uz.step4(update,message);
        }
        if (text.equals("◀️ Ortga qaytish.")) {
            returnMassage = uz.uz(message);
        }
        //keyingisi..  ->step6

        return returnMassage;
    }

   public SendMessage buttonStart(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Tilni tanlang !!!\nВыберите язык !!!");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> rowlist = new ArrayList<>();
        KeyboardRow first = new KeyboardRow();
        KeyboardButton russian = new KeyboardButton();
        russian.setText("\uD83C\uDDF7\uD83C\uDDFA Ru");
        KeyboardButton uzbek = new KeyboardButton();
        uzbek.setText("\uD83C\uDDFA\uD83C\uDDFF Uz");
        first.add(russian);
        first.add(uzbek);
        rowlist.add(first);
        replyKeyboardMarkup.setKeyboard(rowlist);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }
}

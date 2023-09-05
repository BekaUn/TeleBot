package com.project.wecare.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class Uz {
    @Lazy
    @Autowired
    private Button button;
    public SendMessage uz(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(conditionUz());
        sendMessage.setChatId(message.getChatId());
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        KeyboardRow first = new KeyboardRow();
        KeyboardRow second = new KeyboardRow();
        KeyboardButton dale = new KeyboardButton();
        KeyboardButton backButton = new KeyboardButton();
        List<KeyboardRow> rowList = new ArrayList<>();
        dale.setText("▶\uFE0F Keyingisi");
        backButton.setText("◀️ Ortga qaytish");
        first.add(dale);
        second.add(backButton);
        rowList.add(first);
        rowList.add(second);
        replyKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    public SendMessage step4(Update update, Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Step 4");
        sendMessage.setChatId(message.getChatId());
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        KeyboardRow first = new KeyboardRow();
        KeyboardRow second = new KeyboardRow();
        KeyboardButton dale = new KeyboardButton();
        KeyboardButton backButton = new KeyboardButton();
        sendMessage.setChatId(message.getChatId());
        List<KeyboardRow> rowList = new ArrayList<>();
        dale.setText("▶\uFE0F Keyingisi.");
        backButton.setText("◀️ Ortga qaytish.");
        first.add(dale);
        second.add(backButton);
        rowList.add(first);
        rowList.add(second);
        replyKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;

    }

    //agar step 4 dan keyin dalle bosilsa ->
    public SendMessage step5(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Tarifni tanglang:");
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        // Создание кнопок для каждого тарифа
        keyboard.add(createInlineKeyboardRow("3 мес -20%", "tariff_3_months"));
        keyboard.add(createInlineKeyboardRow("6 мес -25%", "tariff_6_months"));
        keyboard.add(createInlineKeyboardRow("9 мес - 32%", "tariff_9_months"));
        keyboard.add(createInlineKeyboardRow("12 мес - 38%", "tariff_12_months"));
        keyboardMarkup.setKeyboard(keyboard);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    private List<InlineKeyboardButton> createInlineKeyboardRow(String text, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callbackData);

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);

        return row;
    }

    private String conditionUz() {
        return "• Ish vaqti: 10:00 dan 21:00 gacha (shanba 10:00 dan 19:00 gacha, yakshanba 10:00 dan 17:00 gacha) " +
                " " +
                "\"3 oy uchun - 20%\\n\" +\n" +
                "\"6 oy uchun - 25%\\n\" +\n" +
                "\"9 oy uchun - 32%\\n\" +\n" +
                "\"12 oy uchun - 38%-43%\\n\" +\n" +
                " " +
                "\"!\\uFE0F Talablar:\\n\" +\n" +
                "\" • Yoshi 18 dan 60 gacha.\\n\" +\n" +
                "\" • Pasport va plastik kartaning amal qilish muddati (kamida 12 oy)\\n\" +\n" +
                "\" • BPI (MIB) bo'yicha qarzlarning yo'qligi\\n\" +\n" +
                "\" • Karta aylanmasi kamida 4 oy bo'lishi kerak (9860, 8600, 5614)\\n\" " +
                "\"✅ Tasdiqni olganingizdan so'ng siz yuborishingiz kerak:\\n\" +\n" +
                "\" • Shartnomani imzolang va skanerlashni guruhga yuboring\\n\" +\n" +
                "\" • Mijoz videoda tasdiqlashi kerak (buyumni qayerda va qachon olgan).\\n\" +\n" +
                " " +
                "\"❌ Toʻlovni toʻlashdan bosh tortish sabablari:\\n\" +\n" +
                "\"Ko'p hollarda muvaffaqiyatsizlik sababi:\\n\" +\n" +
                "\" • Yosh chegarasi: 18 yoshdan 60 yoshgacha.\\n\" +\n" +
                "\" • Salbiy kredit tarixiga ega\\n\" +\n" +
                "\" • Kredit qarzi \\n\" +\n" +
                "\" • BPI qarzi\\n\" +\n" +
                "“ • Oxirgi 4 oy ichida tushumlar past bo‘ldi.";
    }

  /*  private SendPhoto photo(Message message) {
        String fileId = "/home/sardor/Backend/Project/TeleBot/src/main/java/com/project/wecare/images/img.png";
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(message.getChatId());
        sendPhoto.setPhoto(new InputFile(fileId));
        return sendPhoto;
    }*/

   public SendPhoto photo(Update update) {
            Message message = update.getCallbackQuery().getMessage();
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(message.getChatId());
            sendPhoto.setPhoto(new InputFile("img.png"));

            InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
            List<InlineKeyboardButton> row = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText("Sent Photo!");
            row.add(button);
            keyboard.add(row);
            keyboardMarkup.setKeyboard(keyboard);
            sendPhoto.setReplyMarkup(keyboardMarkup);
            return  sendPhoto;
    }
}
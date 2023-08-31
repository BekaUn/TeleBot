package com.project.wecare.util;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class Ru {
    public SendMessage ru(Message message) {
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
        dale.setText("▶\uFE0F Дальше");

        backButton.setText("◀️ Назад");
        first.add(dale);
        second.add(backButton);
        rowList.add(first);
        rowList.add(second);
        replyKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }
    public SendMessage step4(Message message){
        SendMessage sendMessage=new SendMessage();
        sendMessage.setText("" +
                "passportr" +
                "phone number" +
                "!" +
                "!" +
                "Step 4"


        );
        sendMessage.setChatId(message.getChatId());
        return sendMessage;
    }
    public SendMessage step5(Message message){
        SendMessage sendMessage=new SendMessage();
        sendMessage.setText("Step 4");
        sendMessage.setChatId(message.getChatId());
        return sendMessage;
    }

    private String conditionRu() {
        return "• Время работы: с 10:00 до 21:00 (в субботу с 10:00 до 19:00, в воскресенье с 10:00 до 17:00)\n" +
                "\n" +
                "• Условия\n" +
                "    на 3 месяца - 20%\n" +
                "    на 6 месяцев - 25%\n" +
                "    на 9 месяцев - 32%\n" +
                "    на 12 месяцев – 38%-43%\n" +
                "\n" +
                "!\uFE0F Требования:\n" +
                " • Возраст от 18 до 60 лет.\n" +
                " • Срок действия паспорта и пластиковой карты (не менее 12 месяцев)\n" +
                " • Отсутствие долгов в БПИ (MIB)\n" +
                " • Оборот на карте должен быть не менее 4 месяца (9860, 8600, 5614)\n" +
                " \n" +
                "✅ После получения Одобрения должны отправить:\n" +
                " • Подписать договор и отправить скан в группу\n" +
                " • Клиент должен подтвердить в видео (где и когда они получил товар).\n" +
                " \n" +
                "❌ Причины получения отказа в получении рассрочки:\n" +
                "В большинстве случаев причиной отказа является:\n" +
                " • Возрастное ограничение: от 18 до 60 лет.\n" +
                " • Наличие отрицательной кредитной истории\n" +
                " • Кредитная задолженность \n" +
                " • долг в БПИ\n" +
                " • За последние 4 месяца поступления были низкими.";
    }
}

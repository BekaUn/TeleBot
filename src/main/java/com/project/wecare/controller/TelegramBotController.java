package com.project.wecare.controller;

import com.project.wecare.util.Button;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TelegramBotController extends TelegramLongPollingBot {
    private final Button button;
    private Map<Long, Integer> userStates = new HashMap<>();

    // Константы состояний
    private static final int STATE_START = 0;
    private static final int STATE_PASSPORT_FRONT = 1;
    private static final int STATE_PASSPORT_BACK = 2;

    // Добавьте остальные состояния
    @Override
    public void onUpdateReceived(Update update) {
      /*  if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();
            // Обрабатываем коллбэк от InlineKeyboard
            // Можете добавить код для сбора и обработки данных от пользователя
        } else if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Выберите действие:");
            // Создаем InlineKeyboard
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
            List<InlineKeyboardButton> row = new ArrayList<>();
            InlineKeyboardButton qwer = new InlineKeyboardButton();
            qwer.setText("Отправить фото паспорта");
            qwer.setCallbackData("passport");
            row.add(qwer);
            // row.add(new InlineKeyboardButton().setText("Отправить фото паспорта").setCallbackData("passport"));
            // Добавьте остальные кнопки в аналогичном формате
            keyboard.add(row);
            markup.setKeyboard(keyboard);
            message.setReplyMarkup(markup);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
*/

          try {
           execute(button.button(update));
          } catch (TelegramApiException e) {
             throw new RuntimeException(e);
          }


        /*{
            if (update.hasCallbackQuery()) {
                CallbackQuery callbackQuery = update.getCallbackQuery();
                String data = callbackQuery.getData();
                long chatId = callbackQuery.getMessage().getChatId();

                if (data.equals("/start")) {
                    userStates.put(chatId, STATE_PASSPORT_FRONT);
                    sendDocumentRequest(chatId, "1. Загрузите фото паспорта (передняя сторона):");
                } else if (data.equals("next")) {
                    int currentState = userStates.getOrDefault(chatId, STATE_START);
                    int nextState = currentState + 1;
                    userStates.put(chatId, nextState);

                    if (nextState <= STATE_PASSPORT_BACK) {
                        sendDocumentRequest(chatId, nextState, "Загрузите следующий документ:");
                    } else {
                        // Все документы собраны, можно обработать данные
                        // и перейти в другой режим работы или завершить диалог
                        userStates.remove(chatId);
                        SendMessage message = new SendMessage();
                        message.setChatId(chatId);
                        message.setText("Спасибо! Все документы и данные получены.");
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void sendDocumentRequest(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.setText("Выберите действие:");
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton qwer = new InlineKeyboardButton();
        qwer.setText("Отправить фото паспорта");
        qwer.setCallbackData("passport");
        row.add(qwer);
       // row.add(new InlineKeyboardButton().setText("Продолжить").setCallbackData("next"));
        keyboard.add(row);
        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendDocumentRequest(long chatId, int state, String text) {
        // Отправка запроса на загрузку документа с учетом текущего состояния
        // и подходящих инструкций
    }*/


      /*  if (update.hasMessage() && update.getMessage().hasText()) {
            // Пользователь отправил текстовое сообщение, отправляем клавиатуру
            long chatId = update.getMessage().getChatId();
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
            List<InlineKeyboardButton> row = new ArrayList<>();
            InlineKeyboardButton q=new InlineKeyboardButton();
                    q.setText("Загрузить фото");
                    q.setCallbackData("upload_photo");
                    row.add(q);
            // row.add(new InlineKeyboardButton().setText("Загрузить фото").setCallbackData("upload_photo"));
            keyboard.add(row);
            markup.setKeyboard(keyboard);

            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Выберите действие:");
            message.setReplyMarkup(markup);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();
            long chatId = callbackQuery.getMessage().getChatId();

            if (data.equals("upload_photo")) {
                // Отправляем запрос на загрузку фотографии
                SendPhoto photoMessage = new SendPhoto();
                photoMessage.setChatId(chatId);
                InputFile i=new InputFile();
                photoMessage.setPhoto(i); // Замените на ID загруженной фотографии
                try {
                    execute(photoMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }



    @Override
    public String getBotUsername() {
        return "https://t.me/mmm_medical_bot";
    }

    @Override
    public String getBotToken() {
        return "6679898255:AAEOuYpimhr76-zEqUNFlMPo0UGkWrSiLPY";
    }

}

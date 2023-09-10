package com.project.wecare.controller;

import com.project.wecare.entity.User;
import com.project.wecare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class TelegramBotController extends TelegramLongPollingBot {

    private final UserRepository userRepository;
    long groupChatId = -997375479;
    private String NO_BUTTON = "NO";
    private long userId;
    private String PHONE_BUTTON = "Phone";
    private String ACCEPT_BUTTON = "Accept";
    private String STATUSUZ;
    private String STATUSRU;


    @Override
    public void onUpdateReceived(Update update) {
        SendMessage returnMassage = new SendMessage();
        if (update.hasCallbackQuery()) {
            if (update.getCallbackQuery().getData().equals("Yes")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(userId);
                sendMessage.setText("Перефоткайте паспорт");
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }

            }
            if (update.getCallbackQuery().getData().equals("NO")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(userId);
                sendMessage.setText("Перефоткайте пластиковую карту");
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }

            }
            if (update.getCallbackQuery().getData().equals("Phone")) {
                try {
                    phoneNumber(userId);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            if (update.getCallbackQuery().getData().equals("Accept")) {
                try {
                    execute(step5Uz(userId));
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (update.getMessage().hasPhoto()) {
            userId = update.getMessage().getChatId();
            try {
                forwardPhotoToAdmin(update.getMessage());
                yesNoAdmin(groupChatId, "prover");
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            if (STATUSRU.equals("passportPhoto")&& update.getMessage().getPhoto() != null){
                User user = this.userRepository.findByChatId(userId).get();
                returnMassage.setChatId(update.getMessage().getChatId());
                user.setPassportPhoto("image");
                this.userRepository.save(user);
                if (update.getMessage().getPhoto() != null && user.getPassportPhoto() != null) {
                    try {
                        update.getMessage().setPhoto(null);
                        returnMassage.setChatId(update.getMessage().getChatId());
                        step4Ru(update);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
          else   if (STATUSRU.equals("cardPhoto") && update.getMessage().getPhoto() != null){
                User user = this.userRepository.findByChatId(userId).get();
                returnMassage.setChatId(update.getMessage().getChatId());
                user.setCardPhoto("image");
                this.userRepository.save(user);
                if (update.getMessage().getPhoto() != null && user.getCardPhoto() != null) {
                    try {
                        update.getMessage().setPhoto(null);
                        returnMassage.setChatId(update.getMessage().getChatId());
                        step4Ru(update);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
          else if (STATUSUZ.equals("passportPhoto")&& update.getMessage().getPhoto() != null){
                User user = this.userRepository.findByChatId(userId).get();
                returnMassage.setChatId(update.getMessage().getChatId());
                user.setPassportPhoto("image");
                this.userRepository.save(user);
                if (update.getMessage().getPhoto() != null && user.getPassportPhoto() != null) {
                    try {
                        update.getMessage().setPhoto(null);
                        returnMassage.setChatId(update.getMessage().getChatId());
                        step4Uz(update);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
           else if (STATUSUZ.equals("cardPhoto")&& update.getMessage().getPhoto() != null){
                User user = this.userRepository.findByChatId(userId).get();
                returnMassage.setChatId(update.getMessage().getChatId());
                user.setCardPhoto("image");
                this.userRepository.save(user);
                if (update.getMessage().getPhoto() != null && user.getCardPhoto() != null) {
                    try {
                        update.getMessage().setPhoto(null);
                        returnMassage.setChatId(update.getMessage().getChatId());
                        step4Uz(update);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
          /*  if (update.getMessage().getPhoto() != null) {
                downloadPhoto(update);
            }*/

          /*  if (STATUS.equals("passportPhoto")) {
                User user = this.userRepository.findByChatId(userId).get();
                user.setPassportPhoto(update.getMessage().getPhoto().toString());
                this.userRepository.save(user);
            }
            if (STATUS.equals("cardPhoto")) {
                User user = this.userRepository.findByChatId(userId).get();
                user.setCardPhoto(update.getMessage().getPhoto().toString());
                this.userRepository.save(user);
            }*/
            try {
                execute(returnMassage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
      /*  if (update.hasMessage()) {
            if (update.getMessage().hasContact()) {
                if (update.getMessage().getContact().getPhoneNumber() != null) {
                    Optional<User> optional = userRepository.findByChatId(update.getMessage().getChatId());
                    if (optional.isEmpty()) {
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(update.getMessage().getChatId());
                        sendMessage.setText("User doesnt registered ");
                        execute(sendMessage);
                    }
                    User user = optional.get();
                    user.setPhoneNumber(update.getMessage().getContact().getPhoneNumber());
                    userRepository.save(user);
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Ваш номер телефона был зарегестрирован");
                    sendMessage.setChatId(update.getMessage().getChatId());
                    execute(sendMessage);

                }
            }
        }*/

        if (update.getMessage().hasText()) {

            if (update.getMessage().getText().equals("/start")) {
                returnMassage = buttonStart(update.getMessage());
            }

            if (update.getMessage().getText().equals("\uD83C\uDDFA\uD83C\uDDFF Uz")) {
                try {
                    if (!userRepository.existsByChatId(update.getMessage().getChatId())) {
                        register(update.getMessage());
                    }
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                returnMassage = uz(update.getMessage());
            }
            if (update.getMessage().getText().equals("\uD83C\uDDF7\uD83C\uDDFA Ru")) {
                try {
                    if (!userRepository.existsByChatId(update.getMessage().getChatId())) {
                        register(update.getMessage());
                    }
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                returnMassage = ru(update.getMessage());
            }
            if (update.getMessage().getText().equals("◀️ Ortga qaytish") || update.getMessage().getText().equals("◀️ Назад")) {
                returnMassage = buttonStart(update.getMessage());
            }
            if (update.getMessage().getText().equals("▶\uFE0F Keyingisi")) {
                Optional<User> optional = this.userRepository.findByChatId(update.getMessage().getChatId());

                try {
                    User user=optional.get();
                    if (!user.getSuccess().equals("true")) {
                        update.getMessage().setText(null);
                        step4Uz(update);
                    }
                    else {
                        execute(step5Uz(update.getMessage().getChatId()));
                    }
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                if (optional.isEmpty()) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(update.getMessage().getChatId());
                    sendMessage.setText("Foydalanuvchi ro'yxatdan o'tmagan!");
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else if (STATUSUZ != null && STATUSUZ.equals("nemeClinick") && update.getMessage().getText() != "▶\uFE0F Keyingisi" && update.getMessage().getText() != null) {
                    User user = this.userRepository.findByChatId(update.getMessage().getChatId()).get();
                    returnMassage.setChatId(update.getMessage().getChatId());
                    user.setClinicName(update.getMessage().getText());
                    userRepository.save(user);
                    if (update.getMessage().getText() != null && user.getFullTitle() != null) {
                        try {
                            returnMassage.setChatId(update.getMessage().getChatId());
                            step4Uz(update);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                 else if (STATUSUZ != null && STATUSUZ.equals("fulTitle") && update.getMessage().getText() != "▶\uFE0F Keyingisi" && update.getMessage().getText() != null) {
                    User user = this.userRepository.findByChatId(update.getMessage().getChatId()).get();
                    returnMassage.setChatId(update.getMessage().getChatId());
                    user.setFullTitle(update.getMessage().getText());
                    userRepository.save(user);
                    if (update.getMessage().getText() != null && user.getFullTitle() != null) {
                        returnMassage.setChatId(update.getMessage().getChatId());
                        try {
                            step4Uz(update);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else if (STATUSUZ != null && STATUSUZ.equals("money") && update.getMessage().getText() != "▶\uFE0F Keyingisi" && update.getMessage().getText() != null) {
                    User user = this.userRepository.findByChatId(update.getMessage().getChatId()).get();
                    returnMassage.setChatId(update.getMessage().getChatId());
                    user.setOperationCost(Double.valueOf(update.getMessage().getText()));
                    userRepository.save(user);
                    returnMassage.setChatId(update.getMessage().getChatId());
                    try {
                        step4Uz(update);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (STATUSUZ != null && STATUSUZ.equals("location") && update.getMessage().getText() != "▶\uFE0F Keyingisi" && update.getMessage().getText() != null) {
                    User user = this.userRepository.findByChatId(update.getMessage().getChatId()).get();
                    returnMassage.setChatId(update.getMessage().getChatId());
                    user.setRegistration(update.getMessage().getText());
                    userRepository.save(user);
                    returnMassage.setChatId(update.getMessage().getChatId());
                    try {
                        step4Uz(update);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                } else if (STATUSUZ != null && STATUSUZ.equals("phoneNumber") && update.getMessage().getText() != "▶\uFE0F Keyingisi" && update.getMessage().getText() != null) {
                    long chatId = update.getMessage().getChatId();
                    User user = this.userRepository.findByChatId(chatId).get();
                    returnMassage.setChatId(update.getMessage().getChatId());
                    user.setPhoneNumber(update.getMessage().getText());
                    userRepository.save(user);
                    returnMassage.setChatId(update.getMessage().getChatId());
                    try {
                        step4Uz(update);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }

            if (update.getMessage().getText().equals("▶\uFE0F Дальше")) {
                Optional<User> optional = this.userRepository.findByChatId(update.getMessage().getChatId());
                try {
                    User user=optional.get();
                    if (!user.getSuccess().equals("true")) {
                        update.getMessage().setText(null);
                        step4Ru(update);
                    } else {
                        execute(step5Ru(update.getMessage().getChatId()));
                    }
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                if (optional.isEmpty()) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(update.getMessage().getChatId());
                    sendMessage.setText("Пользователь не зарегистрирован!");
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                } else if (STATUSRU != null && STATUSRU.equals("nemeClinick") && update.getMessage().getText() != ("▶\uFE0F Дальше") && update.getMessage().getText() != null) {
                    User user = this.userRepository.findByChatId(update.getMessage().getChatId()).get();
                    returnMassage.setChatId(update.getMessage().getChatId());
                    user.setClinicName(update.getMessage().getText());
                    userRepository.save(user);
                    if (update.getMessage().getText() != null && user.getFullTitle() != null) {
                        returnMassage.setChatId(update.getMessage().getChatId());
                        try {
                            step4Ru(update);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else if (STATUSRU != null && STATUSRU.equals("fulTitle") && update.getMessage().getText() != ("▶\uFE0F Дальше") && update.getMessage().getText() != null) {
                    User user = this.userRepository.findByChatId(update.getMessage().getChatId()).get();
                    returnMassage.setChatId(update.getMessage().getChatId());
                    user.setFullTitle(update.getMessage().getText());
                    userRepository.save(user);
                    if (update.getMessage().getText() != null && user.getFullTitle() != null) {
                        returnMassage.setChatId(update.getMessage().getChatId());
                        try {
                            step4Ru(update);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else if (STATUSRU != null && STATUSRU.equals("money") && update.getMessage().getText() != ("▶\uFE0F Дальше") && update.getMessage().getText() != null) {
                    User user = this.userRepository.findByChatId(update.getMessage().getChatId()).get();
                    returnMassage.setChatId(update.getMessage().getChatId());
                    user.setOperationCost(Double.valueOf(update.getMessage().getText()));
                    userRepository.save(user);
                    returnMassage.setChatId(update.getMessage().getChatId());
                    try {
                        step4Ru(update);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                } else if (STATUSRU != null && STATUSRU.equals("location") && update.getMessage().getText() != ("▶\uFE0F Дальше") && update.getMessage().getText() != null) {
                    User user = this.userRepository.findByChatId(update.getMessage().getChatId()).get();
                    returnMassage.setChatId(update.getMessage().getChatId());
                    user.setRegistration(update.getMessage().getText());
                    userRepository.save(user);
                    returnMassage.setChatId(update.getMessage().getChatId());
                    try {
                        step4Ru(update);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                } else if (STATUSRU != null && STATUSRU.equals("phoneNumber") && update.getMessage().getText() != ("▶\uFE0F Дальше") && update.getMessage().getText() != null) {
                    long chatId = update.getMessage().getChatId();
                    User user = this.userRepository.findByChatId(chatId).get();
                    returnMassage.setChatId(update.getMessage().getChatId());
                    user.setPhoneNumber(update.getMessage().getText());
                    userRepository.save(user);
                    returnMassage.setChatId(update.getMessage().getChatId());
                    try {
                        step4Ru(update);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }



            if (update.getMessage().getText().equals("▶\uFE0F Keyingisi.")) {
                returnMassage = step5Uz(userId);
            }
            if (update.getMessage().getText().equals("◀️ Ortga qaytish..")) {
                try {
                    step4Uz(update);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            if (update.getMessage().getText().equals("◀️ Ortga qaytish.")) {
                returnMassage = uz(update.getMessage());
            }

        }

        try {
            execute(returnMassage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    public void step4Ru(Update update) throws TelegramApiException {
        long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        // sendMessage.setText("vfbfb");
        User user = userRepository.findByChatId(chatId).get();
        if (update.hasCallbackQuery()) {
            update.getCallbackQuery().getMessage().getText();
        }
        else if (user.getRegistration() == null) {
            sendMessage.setChatId(chatId);
            STATUSRU = "location";
            sendMessage.setText("Прописка");
        } else if (user.getPhoneNumber() == null) {
            STATUSRU = "phoneNumber";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Номер телефона привязанный к пластиковой карте");
        } else if (user.getFullTitle() == null) {
            STATUSRU = "fulTitle";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Полное название операции или медицинской услуги");
        } else if (user.getOperationCost() == null) {
            STATUSRU = "money";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Стоимость операции / медицинской услуги");
        } else if (user.getClinicName() == null) {
            STATUSRU = "nemeClinick";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Название клиники");
        }else if (user.getPassportPhoto() == null) {
            STATUSRU = "passportPhoto";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Фото паспорта или ID (передняя и задняя сторона)");
        } else if (user.getCardPhoto() == null) {
            STATUSRU = "cardPhoto";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Фото пластиковой карты заявителя");
        } else if (user.getCardPhoto() != null && user.getPassportPhoto() != null && user.getRegistration() != null
                && user.getPhoneNumber() != null && user.getFullTitle() != null && user.getOperationCost() != null
                && user.getClinicName() != null && user.getPercentage() == null) {
            STATUSRU = null;
            sendMessage.setChatId(chatId);
            sendMessage.setText("Ваша информация отправлена администратору. Ожидайте ответа от администратора.");
        }
        execute(sendMessage);
    }

    public SendMessage step5Uz(long chatId) {
        User user=userRepository.findByChatId(chatId).get();
        user.setSuccess("true");
        userRepository.save(user);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Tarifni tanglang");
        sendPhotoToTelegram(chatId, "", "src/main/resources/image/terifUz.jpg");
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(createInlineKeyboardRow("3 oy -20%", "tariff_3_months"));
        keyboard.add(createInlineKeyboardRow("6 oy -25%", "tariff_6_months"));
        keyboard.add(createInlineKeyboardRow("9 oy - 32%", "tariff_9_months"));
        keyboard.add(createInlineKeyboardRow("12 oy - 38%", "tariff_12_months"));
        keyboardMarkup.setKeyboard(keyboard);
        sendMessage.setChatId(chatId);
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

    public void step4Uz(Update update) throws TelegramApiException {
        long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        // sendMessage.setText("vfbfb");
        User user = userRepository.findByChatId(chatId).get();
        if (update.hasCallbackQuery()) {
            sendMessage.setChatId(chatId);
            update.getCallbackQuery().getMessage().getText();
        }
        else if (user.getRegistration() == null) {
            STATUSUZ = "location";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Roʻyxatda turgan manzilingiz");
        }  else if (user.getPhoneNumber() == null) {
            STATUSUZ = "phoneNumber";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Plastik kartaga ulangan telefon raqami");
        } else if (user.getFullTitle() == null) {
            STATUSUZ = "fulTitle";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Operatsiya yoki tibbiy xizmatning to'liq nomi");
        } else if (user.getOperationCost() == null) {
            STATUSUZ = "money";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Jarrohlik / tibbiy xizmat narxi");
        } else if (user.getClinicName() == null) {
            STATUSUZ = "nemeClinick";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Klinikaning nomi");
        } else if (user.getPassportPhoto() == null) {
            STATUSUZ = "passportPhoto";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Pasport yoki shaxsni tasdiqlovchi fotosurat (old va orqa)");
        }else if (user.getCardPhoto() == null) {
            STATUSUZ = "cardPhoto";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Ariza beruvchining plastik kartasi fotosurati");
        }
        else if (user.getCardPhoto() != null && user.getPassportPhoto() != null && user.getRegistration() != null
                && user.getPhoneNumber() != null && user.getFullTitle() != null && user.getOperationCost() != null
                && user.getClinicName() != null && user.getPercentage() == null) {
            STATUSUZ = null;
            sendMessage.setChatId(chatId);
            sendMessage.setText("Malumotlaringiz adminga yuborildi admindan javon kuting");
        }
         execute(sendMessage);
    }


    public SendMessage step5Ru(long chatId) {
        User user=userRepository.findByChatId(chatId).get();
        user.setSuccess("true");
        userRepository.save(user);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Tarifni tanglang");
        sendPhotoToTelegram(chatId, "", "src/main/resources/image/terifUz.jpg");
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(createInlineKeyboardRow("3 мес -20%", "tariff_3_months"));
        keyboard.add(createInlineKeyboardRow("6 мес -25%", "tariff_6_months"));
        keyboard.add(createInlineKeyboardRow("9 мес - 32%", "tariff_9_months"));
        keyboard.add(createInlineKeyboardRow("12 мес - 38%", "tariff_12_months"));
        keyboardMarkup.setKeyboard(keyboard);
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    private void sendPhotoToTelegram(long chatId, String photoCaption, String photoFilePath) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(String.valueOf(chatId));
        sendPhoto.setCaption(photoCaption);

        // Load the photo from the file path and set it in the sendPhoto request
        File photoFile = new File(photoFilePath);
        try {
            if (photoFile.exists()) {
                InputFile photoInputFile = new InputFile(photoFile);
                sendPhoto.setPhoto(photoInputFile);
                execute(sendPhoto); // Send the photo
            } else {
                // Handle the case where the photo file does not exist
                System.err.println("Photo file does not exist: " + photoFilePath);
            }
        } catch (TelegramApiException e) {
            // Handle any exceptions that may occur while sending the photo
            e.printStackTrace();
        }
    }

    private void phoneNumber(long chatId) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Please enter your phone number");
        sendMessage.setChatId(chatId);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> list = new ArrayList<>();
        KeyboardRow first = new KeyboardRow();
        KeyboardButton number = new KeyboardButton();
        KeyboardButton button = new KeyboardButton();
        number.setText("\uD83D\uDCDE Phone Number");
        number.setRequestContact(true);
        button.setText("▶\uFE0F Keyingisi");
        first.add(button);
        first.add(number);
        list.add(first);
        replyKeyboardMarkup.setKeyboard(list);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        execute(sendMessage);
    }

    private void register(Message message) throws TelegramApiException {
        long chatId = message.getChatId();
        Optional<User> optional = userRepository.findByChatId(chatId);
        if (optional.isPresent()) {
            return;
        }
        User user = new User();
        user.setChatId(chatId);
        user.setSuccess("false");
        if (message.getChat().getUserName() != null) {
            user.setUsername(message.getChat().getUserName());
        }
        userRepository.save(user);
    }

    private void yesNoAdmin(long chatId, String text) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var payment = new InlineKeyboardButton();
        payment.setText("Перефоткать паспорт");
        String YES_BUTTON = "Yes";
        payment.setCallbackData(YES_BUTTON);
        var back = new InlineKeyboardButton();
        back.setText("Перефоткать пластиковую карту");
        back.setCallbackData(NO_BUTTON);
        var phoneNum = new InlineKeyboardButton();
        phoneNum.setText("Введите номер телефона");
        phoneNum.setCallbackData(PHONE_BUTTON);
        var acceptBut = new InlineKeyboardButton();
        acceptBut.setText("Документы принять");
        acceptBut.setCallbackData(ACCEPT_BUTTON);
        rowInLine.add(payment);
        rowInLine.add(back);
        rowInLine.add(phoneNum);
        rowInLine.add(acceptBut);
        rowsInLine.add(rowInLine);
        markupInLine.setKeyboard(rowsInLine);
        sendMessage.setReplyMarkup(markupInLine);
        execute(sendMessage);
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

    private void forwardPhotoToAdmin(Message message) throws TelegramApiException {
        ForwardMessage forwardMessage = new ForwardMessage();
        forwardMessage.setChatId(groupChatId);
        forwardMessage.setFromChatId(message.getChatId());
        forwardMessage.setMessageId(message.getMessageId());
        execute(forwardMessage);
    }

    @SneakyThrows

    @Override
    public String getBotUsername() {
        return "https://t.me/mmm_medical_bot";
    }

    @Override
    public String getBotToken() {
        return "6679898255:AAEOuYpimhr76-zEqUNFlMPo0UGkWrSiLPY";
    }

    private void downloadPhoto(Update update) throws IOException {
        PhotoSize photoSize = update.getMessage().getPhoto().get(0);
        String fileId = photoSize.getFileId();
        // Получите URL изображения
        String imageUrl = "https://api.telegram.org/file/6679898255:AAEOuYpimhr76-zEqUNFlMPo0UGkWrSiLPY/" + fileId;

        // Откройте поток для скачивания изображения
        InputStream imageStream = new URL(imageUrl).openStream();

        // Укажите путь к ресурсной папке и имя файла
        String resourceFolderPath = "src/main/resources/";
        String fileName = "image1.jpg";

        // Создайте файл для сохранения
        File output = new File(resourceFolderPath + fileName);

        // Скопируйте изображение в файл
        Files.copy(imageStream, output.toPath());

        // File destination = new File("resources/" + UUID.randomUUID().toString() + ".jpg");
      /*  BufferedImage image = ImageIO.read(new File(photo.get(2).getFilePath()));
        String resourceFolderPath = "src/main/resources/image/";
        String fileName = "новое_имя_изображения.jpg";
        File output = new File(resourceFolderPath + fileName);

        // Сохраните изображение
        ImageIO.write(image, "jpg", output);*/

      /*  try (FileOutputStream fos = new FileOutputStream(destination)) {
            Update update = null;

            InputFile inputFile = new InputFile(filePath);
            List<PhotoSize> photo= update.getMessage().getPhoto();
            byte[] fileBytes = inputFile.toString().getBytes();
            fos.write(fileBytes);
        }*/
        //   return destination;
    }

}

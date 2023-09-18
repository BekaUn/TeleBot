package com.project.wecare.controller;

import com.project.wecare.entity.User;
import com.project.wecare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.GetFile;
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
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class TelegramBotController extends TelegramLongPollingBot {
    private final UserRepository userRepository;
    long groupChatId = -1001983667230L;
    private String photoPath = "/home/sardor/Backend/Project/TeleBot/src/main/resources/passport/";
    private String PASSPORT_BUTTON = "Passport";
    private String NO_BUTTON = "NO";
    private long userId;
    private String PHONE_BUTTON = "Phone";
    private String ACCEPT_BUTTON = "Accept";
    private String STATUSUZ;
    private String STATUSRU;
    private String LANGUAGE;
    private String LOCATION = "loc";
    private String PAYMENT = "pay";
    private String CLINIC = "clinic";
    private String OPERATION = "operation";
    private String LOCATION_ACCEPT = "loc ACC";
    private String PAYMENT_ACCEPT = "pay ACC";
    private String CLINIC_ACCEPT = "clinic ACC";
    private String OPERATION_ACCEPT = "operation ACC";
    private String PHONE_BUTTON_ACCEPT = "Phone ACC";

    private String PASSPORT_BUTTON_BACK = "Passport_back";
    private String NO_BUTTON_BACK = "NO_BACK";

    private String CARD = "card";
    private String CARDA = "card ACC";
    private String REJECT = "reject";

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage returnMassage = new SendMessage();
        if (update.hasCallbackQuery()) {
            if (update.getCallbackQuery().getData().equals("Passport")) {

                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(userId);
                sendMessage.setText("Качество фотографии передней стороны паспорта не соответствует требованиям, отправьте фотографию в хорошем качестве.");
                User user = userRepository.findByChatId(userId).get();
                user.setPassportPhoto1(null);
                STATUSRU = "passportPhoto1";
                userRepository.save(user);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            if (update.getCallbackQuery().getData().equals("NO")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(userId);
                sendMessage.setText("Фото передней части паспорта принято");

                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }

            }
            if (update.getCallbackQuery().getData().equals("Phone")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(userId);
                sendMessage.setText("Отправьте заново номер телефона");
                User user = userRepository.findByChatId(userId).get();
                user.setPhoneNumber(null);
                STATUSRU = "phoneNumber";
                userRepository.save(user);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            if (update.getCallbackQuery().getData().equals("Accept")) {
                try {
                    if (LANGUAGE.equals("uz")) {
                        execute(step5Uz(userId));
                    } else if (LANGUAGE.equals("ru")) {
                        execute(step5Ru(userId));
                    }
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }

            if (update.getCallbackQuery().getData().equals("loc")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(userId);
                sendMessage.setText("Отправьте заново прописку");
                User user = userRepository.findByChatId(userId).get();
                user.setRegistration(null);
                STATUSRU = "location";
                userRepository.save(user);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            if (update.getCallbackQuery().getData().equals("loc ACC")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(userId);
                sendMessage.setText("Пропсика принета");
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }

            if (update.getCallbackQuery().getData().equals("pay")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(userId);
                sendMessage.setText("Отправьте заново стоимость операции");
                User user = userRepository.findByChatId(userId).get();
                user.setOperationCost(null);
                STATUSRU = "money";
                userRepository.save(user);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }

            if (update.getCallbackQuery().getData().equals("pay ACC")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(userId);
                sendMessage.setText("Стоимость операции была принета");
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            if (update.getCallbackQuery().getData().equals("reject")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(userId);
                sendMessage.setText("Вам отказано в кредите");
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }

            if (update.getCallbackQuery().getData().equals("clinic")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(userId);
                sendMessage.setText("Отправьте заново название клиники");
                User user = userRepository.findByChatId(userId).get();
                user.setClinicName(null);
                STATUSRU = "nemeClinick";
                userRepository.save(user);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            if (update.getCallbackQuery().getData().equals("clinic ACC")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(userId);
                sendMessage.setText("Название клиники успешно приянто");
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            if (update.getCallbackQuery().getData().equals("operation")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(userId);
                sendMessage.setText("Отправьте заново название операции");
                User user = userRepository.findByChatId(userId).get();
                user.setFullTitle(null);
                STATUSRU = "fulTitle";
                userRepository.save(user);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            if (update.getCallbackQuery().getData().equals("operation ACC")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(userId);
                sendMessage.setText("Название операции успешно принято");
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }

            if (update.getCallbackQuery().getData().equals("Passport_back")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(userId);
                sendMessage.setText("Качество фотографии задней стороны паспорта не соответствует требованиям, отправьте фотографию в хорошем качестве.");
                User user = userRepository.findByChatId(userId).get();
                user.setPassportPhoto2(null);
                STATUSRU = "passportPhoto2";
                userRepository.save(user);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }

            if (update.getCallbackQuery().getData().equals("NO_BACK")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(userId);
                sendMessage.setText("Задняя сторона паспорта принята");
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }

            if (update.getCallbackQuery().getData().equals("card")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(userId);
                sendMessage.setText("Качество фотографии пластиковой карты не соответствует требованиям, отправьте фотографию в хорошем качестве.");
                User user = userRepository.findByChatId(userId).get();
                user.setCardPhoto(null);
                STATUSRU = "cardPhoto";
                userRepository.save(user);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }

            if (update.getCallbackQuery().getData().equals("card ACC")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(userId);
                sendMessage.setText("Фаото пластиковой карты принято");
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }

            if (update.getCallbackQuery().getData().equals("tariff_3_months")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(groupChatId);

                User user = userRepository.findByChatId(userId).get();
                user.setPercentage("3 oy -20%");
                userRepository.save(user);
                sendMessage.setText("3 oy -20%" + "\n\n" + user.getFirstname() + " " + user.getLastname());
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            if (update.getCallbackQuery().getData().equals("tariff_6_months")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(groupChatId);

                User user = userRepository.findByChatId(userId).get();
                user.setPercentage("6 oy -25%");
                userRepository.save(user);
                sendMessage.setText("6 oy -25%" + "\n\n" + user.getFirstname() + " " + user.getLastname());
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            if (update.getCallbackQuery().getData().equals("tariff_9_months")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(groupChatId);

                User user = userRepository.findByChatId(userId).get();
                user.setPercentage("9 oy -32%");
                userRepository.save(user);
                sendMessage.setText("9 oy -32%" + "\n\n" + user.getFirstname() + " " + user.getLastname());
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            if (update.getCallbackQuery().getData().equals("tariff_12_months")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(groupChatId);

                User user = userRepository.findByChatId(userId).get();
                user.setPercentage("12 oy -38%");
                userRepository.save(user);
                sendMessage.setText("12 oy -38%" + "\n\n" + user.getFirstname() + " " + user.getLastname());
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }


        }


        if (update.hasMessage()) {

            if (update.getMessage().hasPhoto()) {
                List<PhotoSize> photoSizes = update.getMessage().getPhoto();
                PhotoSize photoSize = photoSizes.get(photoSizes.size() - 1);

                GetFile getFile = new GetFile(photoSize.getFileId());
                try {
                    userId = update.getMessage().getChatId();
                    User user = userRepository.findByChatId(update.getMessage().getChatId()).get();

                    org.telegram.telegrambots.meta.api.objects.File file = execute(getFile);
                    String fileURL = file.getFileUrl(getBotToken());

                    URL url = new URL(fileURL);
                    InputStream inputStream = url.openStream();


                    FileUtils.copyInputStreamToFile(inputStream, new File("/home/sardor/Backend/Project/TeleBot/src/main/resources/passport/" + photoSize.getWidth().toString() + " x " + photoSize.getHeight().toString() + ".jpg"));
                    if (STATUSUZ != null) {
                        if (STATUSUZ.equals("passportPhoto1") && update.getMessage().getPhoto() != null) {
                            user.setPassportPhoto1(photoPath + photoSize.getWidth().toString() + " x " + photoSize.getHeight().toString() + ".jpg");
                            userRepository.save(user);
                            update.getMessage().setPhoto(null);
                            returnMassage.setChatId(update.getMessage().getChatId());
                            step4Uz(update);
                            forwardPhotoToAdmin(update.getMessage());
                            yesNoAdmin(groupChatId, user.getFirstname() + " " + user.getLastname() + "\n " + user.getChatId());


                        }
                    }
                    if (STATUSRU != null) {
                        if (STATUSRU.equals("passportPhoto1") && update.getMessage().getPhoto() != null) {
                            user.setPassportPhoto1(photoPath + photoSize.getWidth().toString() + " x " + photoSize.getHeight().toString() + ".jpg");
                            userRepository.save(user);
                            update.getMessage().setPhoto(null);
                            returnMassage.setChatId(update.getMessage().getChatId());
                            step4Ru(update);
                            forwardPhotoToAdmin(update.getMessage());
                            yesNoAdmin(groupChatId, user.getFirstname() + " " + user.getLastname() + "\n " + user.getChatId());
                        }
                    }
                    if (STATUSUZ != null) {
                        if (STATUSUZ.equals("passportPhoto2") && update.getMessage().getPhoto() != null) {
                            user.setPassportPhoto2(photoPath + photoSize.getWidth().toString() + " x " + photoSize.getHeight().toString() + ".jpg");
                            userRepository.save(user);
                            update.getMessage().setPhoto(null);
                            returnMassage.setChatId(update.getMessage().getChatId());
                            step4Uz(update);
                            forwardPhotoToAdmin(update.getMessage());
                            pasportBack(groupChatId, user.getFirstname() + " " + user.getLastname() + "\n " + user.getChatId());
                        }
                    }
                    if (STATUSRU != null) {
                        if (STATUSRU.equals("passportPhoto2") && update.getMessage().getPhoto() != null) {
                            user.setPassportPhoto2(photoPath + photoSize.getWidth().toString() + " x " + photoSize.getHeight().toString() + ".jpg");
                            userRepository.save(user);
                            update.getMessage().setPhoto(null);
                            returnMassage.setChatId(update.getMessage().getChatId());
                            step4Ru(update);
                            forwardPhotoToAdmin(update.getMessage());
                            pasportBack(groupChatId, user.getFirstname() + " " + user.getLastname() + "\n " + user.getChatId());
                        }
                    }

                    if (STATUSUZ != null) {
                        if (STATUSUZ.equals("cardPhoto") && update.getMessage().getPhoto() != null) {
                            user.setCardPhoto(photoPath + photoSize.getWidth().toString() + " x " + photoSize.getHeight().toString() + ".jpg");
                            userRepository.save(user);
                            update.getMessage().setPhoto(null);
                            returnMassage.setChatId(update.getMessage().getChatId());
                            step4Uz(update);
                            forwardPhotoToAdmin(update.getMessage());
                            cardYes(groupChatId, "\n\n" + user.getFirstname() + " " + user.getLastname() + "\n " + user.getChatId());

                        }
                    }
                    if (STATUSRU != null) {
                        if (STATUSRU.equals("cardPhoto") && update.getMessage().getPhoto() != null) {
                            user.setCardPhoto(photoPath + photoSize.getWidth().toString() + " x " + photoSize.getHeight().toString() + ".jpg");
                            userRepository.save(user);
                            update.getMessage().setPhoto(null);
                            returnMassage.setChatId(update.getMessage().getChatId());
                            step4Ru(update);
                            forwardPhotoToAdmin(update.getMessage());
                            cardYes(groupChatId, user.getFirstname() + " " + user.getLastname() + "\n " + user.getChatId());
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (update.getMessage().hasDocument()) {
                try {
                    if (update.getMessage().getChatId().equals(groupChatId)) {
                        if (update.getMessage().getCaption() == null) {
                            SendMessage sendMessage = new SendMessage();
                            sendMessage.setChatId(groupChatId);
                            sendMessage.setText("Вы не дали чат айди пользователя");
                            execute(sendMessage);
                        }
                        String caption = update.getMessage().getCaption();
                        long cap = Long.parseLong(caption);
                        Optional<User> optional = userRepository.findByChatId(cap);
                        if (optional.isEmpty()) {
                            SendMessage sendMessage = new SendMessage();
                            sendMessage.setChatId(groupChatId);
                            sendMessage.setText("Вы не дали не существующий чат айди пользователя");
                            execute(sendMessage);
                            return;
                        }
                        forwardPhotoToUser(update.getMessage(), caption);
                        if (LANGUAGE != null) {
                            if (LANGUAGE.equals("ru")) {
                                SendMessage sendMessage = new SendMessage();
                                sendMessage.setChatId(caption);
                                sendMessage.setText("Ваш договор готов. Пожалуйста распечатайте полученный файл, подпишите и направьте нам отсканированную версию. ");
                                execute(sendMessage);
                            }
                            if (LANGUAGE.equals("uz")) {
                                SendMessage sendMessage = new SendMessage();
                                sendMessage.setChatId(caption);
                                sendMessage.setText("Shartnomangiz tayyor. Iltimos shatnomani tasdiqlab, bizga skaner variantini yuborin. ");
                                execute(sendMessage);
                            }

                        } else if (LANGUAGE == null) {
                            SendMessage sendMessage = new SendMessage();
                            sendMessage.setChatId(caption);
                            sendMessage.setText("Ваш договор готов. Пожалуйста распечатайте полученный файл, подпишите и направьте нам отсканированную версию. ");
                            execute(sendMessage);

                        }
                        return;
                    }
                    if (update.getMessage().getChatId() != groupChatId) {
                        forwardPhotoToAdmin(update.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
                try {
                    execute(returnMassage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            if (update.getMessage().hasText()) {
                String textMes = update.getMessage().getText();
                String[] split = textMes.split(" ");
                if (update.getMessage().getChatId().equals(groupChatId)) {
                    if (split[0].equals("/chat")) {
                        long chatId = Long.parseLong(split[1]);
                        Optional<User> optional = userRepository.findByChatId(chatId);
                        if (optional.isEmpty()) {
                            SendMessage sendMessage = new SendMessage();
                            sendMessage.setChatId(groupChatId);
                            sendMessage.setText("Пользователь не найден");
                            try {
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                            return;
                        }
                        User user = optional.get();
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(groupChatId);
                        String textToSend = user.toString();
                        sendPhotoToTelegram(groupChatId, user.getUsername(), user.getPassportPhoto1());
                        sendPhotoToTelegram(groupChatId, user.getUsername(), user.getPassportPhoto2());

                        sendPhotoToTelegram(groupChatId, user.getUsername(), user.getCardPhoto());
                        sendMessage.setText(textToSend);
                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
                if (update.getMessage().getText().equals("/send")) {
                    SendMessage sendMessage = new SendMessage();
                    if (update.getMessage().getChatId().equals(groupChatId)) ;
                    var users = userRepository.findAll();
                    String textToSend = users.toString();

                    for (User user1 : users) {
                        sendPhotoToTelegram(groupChatId, user1.getUsername(), user1.getPassportPhoto1());
                        sendPhotoToTelegram(groupChatId, user1.getUsername(), user1.getCardPhoto());

                    }
                    sendMessage.setText(textToSend);
                    sendMessage.setChatId(groupChatId);
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (STATUSRU != null) {
                    if (STATUSRU.equals("location")) {
                        userId = update.getMessage().getChatId();
                        User userFind = userRepository.findByChatId(update.getMessage().getChatId()).get();

                        String message = update.getMessage().getText() + "\n " + userFind.getFirstname() + " " + userFind.getLastname() + "\n " + userFind.getChatId();

                        try {
                            loactionYes(groupChatId, message);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                if (STATUSRU != null) {
                    if (STATUSRU.equals("phoneNumber")) {
                        userId = update.getMessage().getChatId();
                        User userFind = userRepository.findByChatId(update.getMessage().getChatId()).get();
                        String message = update.getMessage().getText() + "\n " + userFind.getFirstname() + " " + userFind.getLastname() + "\n " + userFind.getChatId();

                        try {
                            phoneYes(groupChatId, message);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                if (STATUSRU != null) {
                    if (STATUSRU.equals("money")) {
                        userId = update.getMessage().getChatId();
                        User userFind = userRepository.findByChatId(update.getMessage().getChatId()).get();

                        String message = update.getMessage().getText() + "\n " + userFind.getFirstname() + " " + userFind.getLastname() + "\n " + userFind.getChatId();

                        try {
                            moneyYes(groupChatId, message);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                if (STATUSRU != null) {
                    if (STATUSRU.equals("nemeClinick")) {
                        userId = update.getMessage().getChatId();
                        User userFind = userRepository.findByChatId(update.getMessage().getChatId()).get();

                        String message = update.getMessage().getText() + "\n " + userFind.getFirstname() + " " + userFind.getLastname() + "\n " + userFind.getChatId();

                        try {
                            clinicYes(groupChatId, message);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
                if (STATUSRU != null) {
                    if (STATUSRU.equals("fulTitle")) {
                        userId = update.getMessage().getChatId();
                        User userFind = userRepository.findByChatId(update.getMessage().getChatId()).get();
                        String message = update.getMessage().getText() + "\n " + userFind.getFirstname() + " " + userFind.getLastname() + "\n " + userFind.getChatId();
                        try {
                            operationYes(groupChatId, message);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }

                if (update.getMessage().getText().equals("/start")) {
                    returnMassage = buttonStart(update.getMessage());
                }

                if (update.getMessage().getText().equals("\uD83C\uDDFA\uD83C\uDDFF Uz")) {
                    try {
                        if (!userRepository.existsByChatId(update.getMessage().getChatId())) {
                            LANGUAGE = "uz";
                            register(update.getMessage());
                        }
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    returnMassage = uz(update.getMessage());
                }
                if (update.getMessage().getText().equals("▶\uFE0F Keyingisi.")) {
                    returnMassage = newStepUz(update.getMessage());
                }
                if (update.getMessage().getText().equals("\uD83C\uDDF7\uD83C\uDDFA Ru")) {
                    try {
                        if (!userRepository.existsByChatId(update.getMessage().getChatId())) {
                            LANGUAGE = "ru";
                            register(update.getMessage());
                        }
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    returnMassage = ru(update.getMessage());
                }
                if (update.getMessage().getText().equals("▶\uFE0F Дальше.")) {
                    returnMassage = newStepRu(update.getMessage());
                }
                if (update.getMessage().getText().equals("◀️ Ortga qaytish") || update.getMessage().getText().equals("◀️ Назад")) {
                    STATUSUZ = null;
                    STATUSRU = null;
                    LANGUAGE = null;
                    returnMassage = buttonStart(update.getMessage());
                }
                if (update.getMessage().getText().equals("▶\uFE0F Keyingisi")) {
                    Optional<User> optional = this.userRepository.findByChatId(update.getMessage().getChatId());
                    try {
                        User user = optional.get();
                        if (!user.getSuccess().equals("true")) {
                            update.getMessage().setText(null);
                            step4Uz(update);
                        } else {
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
                } else if (STATUSUZ != null && STATUSUZ.equals("firstname") && !update.getMessage().getText().equals("▶\uFE0F Keyingisi") && update.getMessage().getText() != null && !update.getMessage().getText().equals("◀️ Ortga qaytish") && !update.getMessage().getText().equals("\uD83C\uDDFA\uD83C\uDDFF Uz")
                        && !update.getMessage().getText().equals("◀️ Назад") && !update.getMessage().getText().equals("\uD83C\uDDF7\uD83C\uDDFA Ru") && !update.getMessage().getText().equals("/start")) {
                    User user = this.userRepository.findByChatId(update.getMessage().getChatId()).get();
                    returnMassage.setChatId(update.getMessage().getChatId());
                    user.setFirstname(update.getMessage().getText());
                    this.userRepository.save(user);
                    if (update.getMessage().getText() != null && user.getFirstname() != null) {
                        try {
                            returnMassage.setChatId(update.getMessage().getChatId());
                            step4Uz(update);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else if (STATUSUZ != null && STATUSUZ.equals("lastname") && !update.getMessage().getText().equals("▶\uFE0F Keyingisi") && update.getMessage().getText() != null && !update.getMessage().getText().equals("◀️ Ortga qaytish") && !update.getMessage().getText().equals("\uD83C\uDDFA\uD83C\uDDFF Uz")
                        && !update.getMessage().getText().equals("◀️ Назад") && !update.getMessage().getText().equals("\uD83C\uDDF7\uD83C\uDDFA Ru") && !update.getMessage().getText().equals("/start")) {
                    User user = this.userRepository.findByChatId(update.getMessage().getChatId()).get();
                    returnMassage.setChatId(update.getMessage().getChatId());
                    user.setLastname(update.getMessage().getText());
                    this.userRepository.save(user);
                    if (update.getMessage().getText() != null && user.getLastname() != null) {
                        try {
                            returnMassage.setChatId(update.getMessage().getChatId());
                            step4Uz(update);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else if (STATUSUZ != null && STATUSUZ.equals("nemeClinick") && !update.getMessage().getText().equals("▶\uFE0F Keyingisi") && update.getMessage().getText() != null && !update.getMessage().getText().equals("◀️ Ortga qaytish") && !update.getMessage().getText().equals("\uD83C\uDDFA\uD83C\uDDFF Uz")
                        && !update.getMessage().getText().equals("◀️ Назад") && !update.getMessage().getText().equals("\uD83C\uDDF7\uD83C\uDDFA Ru") && !update.getMessage().getText().equals("/start")) {
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
                } else if (STATUSUZ != null && STATUSUZ.equals("fulTitle") && !update.getMessage().getText().equals("▶\uFE0F Keyingisi") && update.getMessage().getText() != null && !update.getMessage().getText().equals("◀️ Ortga qaytish") && !update.getMessage().getText().equals("\uD83C\uDDFA\uD83C\uDDFF Uz")
                        && !update.getMessage().getText().equals("◀️ Назад") && !update.getMessage().getText().equals("\uD83C\uDDF7\uD83C\uDDFA Ru") && !update.getMessage().getText().equals("/start")) {
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
                } else if (STATUSUZ != null && STATUSUZ.equals("money") && !update.getMessage().getText().equals("▶\uFE0F Keyingisi") && update.getMessage().getText() != null && !update.getMessage().getText().equals("◀️ Ortga qaytish") && !update.getMessage().getText().equals("\uD83C\uDDFA\uD83C\uDDFF Uz")
                        && !update.getMessage().getText().equals("◀️ Назад") && !update.getMessage().getText().equals("\uD83C\uDDF7\uD83C\uDDFA Ru") && !update.getMessage().getText().equals("/start")) {
                    User user = this.userRepository.findByChatId(update.getMessage().getChatId()).get();
                    returnMassage.setChatId(update.getMessage().getChatId());
                    user.setOperationCost(update.getMessage().getText());
                    userRepository.save(user);
                    returnMassage.setChatId(update.getMessage().getChatId());
                    try {
                        step4Uz(update);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                } else if (STATUSUZ != null && STATUSUZ.equals("location") && !update.getMessage().getText().equals("▶\uFE0F Keyingisi") && update.getMessage().getText() != null && !update.getMessage().getText().equals("◀️ Ortga qaytish") && !update.getMessage().getText().equals("\uD83C\uDDFA\uD83C\uDDFF Uz")
                        && !update.getMessage().getText().equals("◀️ Назад") && !update.getMessage().getText().equals("\uD83C\uDDF7\uD83C\uDDFA Ru") && !update.getMessage().getText().equals("/start")) {
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
                } else if (STATUSUZ != null && STATUSUZ.equals("phoneNumber") && !update.getMessage().getText().equals("▶\uFE0F Keyingisi") && update.getMessage().getText() != null && !update.getMessage().getText().equals("◀️ Ortga qaytish") && !update.getMessage().getText().equals("\uD83C\uDDFA\uD83C\uDDFF Uz")
                        && !update.getMessage().getText().equals("◀️ Назад") && !update.getMessage().getText().equals("\uD83C\uDDF7\uD83C\uDDFA Ru") && !update.getMessage().getText().equals("/start")) {
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
                        User user = optional.get();
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
                } else if (STATUSRU != null && STATUSRU.equals("firstname") && !update.getMessage().getText().equals("▶\uFE0F Дальше") && update.getMessage().getText() != null && !update.getMessage().getText().equals("◀️ Назад") && !update.getMessage().getText().equals("\uD83C\uDDF7\uD83C\uDDFA Ru")
                        && !update.getMessage().getText().equals("◀️ Ortga qaytish") && !update.getMessage().getText().equals("\uD83C\uDDFA\uD83C\uDDFF Uz") && !update.getMessage().getText().equals("/start")) {
                    User user = this.userRepository.findByChatId(update.getMessage().getChatId()).get();
                    returnMassage.setChatId(update.getMessage().getChatId());
                    user.setFirstname(update.getMessage().getText());
                    this.userRepository.save(user);
                    if (update.getMessage().getText() != null && user.getFirstname() != null) {
                        returnMassage.setChatId(update.getMessage().getChatId());
                        try {
                            step4Ru(update);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else if (STATUSRU != null && STATUSRU.equals("lastname") && !update.getMessage().getText().equals("▶\uFE0F Дальше") && update.getMessage().getText() != null && !update.getMessage().getText().equals("◀️ Назад") && !update.getMessage().getText().equals("\uD83C\uDDF7\uD83C\uDDFA Ru")
                        && !update.getMessage().getText().equals("◀️ Ortga qaytish") && !update.getMessage().getText().equals("\uD83C\uDDFA\uD83C\uDDFF Uz") && !update.getMessage().getText().equals("/start")) {
                    User user = this.userRepository.findByChatId(update.getMessage().getChatId()).get();
                    returnMassage.setChatId(update.getMessage().getChatId());
                    user.setLastname(update.getMessage().getText());
                    this.userRepository.save(user);
                    if (update.getMessage().getText() != null && user.getLastname() != null) {
                        returnMassage.setChatId(update.getMessage().getChatId());
                        try {
                            step4Ru(update);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else if (STATUSRU != null && STATUSRU.equals("nemeClinick") && !update.getMessage().getText().equals("▶\uFE0F Дальше") && update.getMessage().getText() != null && !update.getMessage().getText().equals("◀️ Назад") && !update.getMessage().getText().equals("\uD83C\uDDF7\uD83C\uDDFA Ru")
                        && !update.getMessage().getText().equals("◀️ Ortga qaytish") && !update.getMessage().getText().equals("\uD83C\uDDFA\uD83C\uDDFF Uz") && !update.getMessage().getText().equals("/start")) {
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
                } else if (STATUSRU != null && STATUSRU.equals("fulTitle") && !update.getMessage().getText().equals("▶\uFE0F Дальше") && update.getMessage().getText() != null && !update.getMessage().getText().equals("◀️ Назад") && !update.getMessage().getText().equals("\uD83C\uDDF7\uD83C\uDDFA Ru")
                        && !update.getMessage().getText().equals("◀️ Ortga qaytish") && !update.getMessage().getText().equals("\uD83C\uDDFA\uD83C\uDDFF Uz") && !update.getMessage().getText().equals("/start")) {
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
                } else if (STATUSRU != null && STATUSRU.equals("money") && !update.getMessage().getText().equals("▶\uFE0F Дальше") && update.getMessage().getText() != null && !update.getMessage().getText().equals("◀️ Назад") && !update.getMessage().getText().equals("\uD83C\uDDF7\uD83C\uDDFA Ru")
                        && !update.getMessage().getText().equals("◀️ Ortga qaytish") && !update.getMessage().getText().equals("\uD83C\uDDFA\uD83C\uDDFF Uz") && !update.getMessage().getText().equals("/start")) {
                    User user = this.userRepository.findByChatId(update.getMessage().getChatId()).get();
                    returnMassage.setChatId(update.getMessage().getChatId());
                    user.setOperationCost(update.getMessage().getText());
                    userRepository.save(user);
                    returnMassage.setChatId(update.getMessage().getChatId());
                    try {
                        step4Ru(update);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                } else if (STATUSRU != null && STATUSRU.equals("location") && !update.getMessage().getText().equals("▶\uFE0F Дальше") && update.getMessage().getText() != null && !update.getMessage().getText().equals("◀️ Назад") && !update.getMessage().getText().equals("\uD83C\uDDF7\uD83C\uDDFA Ru")
                        && !update.getMessage().getText().equals("◀️ Ortga qaytish") && !update.getMessage().getText().equals("\uD83C\uDDFA\uD83C\uDDFF Uz") && !update.getMessage().getText().equals("/start")) {
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
                } else if (STATUSRU != null && STATUSRU.equals("phoneNumber") && !update.getMessage().getText().equals("▶\uFE0F Дальше") && update.getMessage().getText() != null && !update.getMessage().getText().equals("◀️ Назад") && !update.getMessage().getText().equals("\uD83C\uDDF7\uD83C\uDDFA Ru")
                        && !update.getMessage().getText().equals("◀️ Ortga qaytish") && !update.getMessage().getText().equals("\uD83C\uDDFA\uD83C\uDDFF Uz") && !update.getMessage().getText().equals("/start")) {
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
                if (update.getMessage().hasText()) {
                    if (update.getMessage().getText().equals("◀️ Ortga qaytish.")) {
                        returnMassage = uz(update.getMessage());
                    }
                }
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
        dale.setText("▶\uFE0F Keyingisi.");
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
        User user = userRepository.findByChatId(chatId).get();
        if (update.hasCallbackQuery()) {
            update.getCallbackQuery().getMessage().getText();
        } else if (user.getFirstname() == null) {
            STATUSRU = "firstname";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Введите имя");
        } else if (user.getLastname() == null) {
            STATUSRU = "lastname";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Введите фамилию");
        } else if (user.getPassportPhoto1() == null) {
            STATUSRU = "passportPhoto1";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Отправьте фотографию паспорта / ID");
        } else if (user.getPassportPhoto2() == null) {
            STATUSRU = "passportPhoto2";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Отправьте заднюю сторону паспорта / ID");
        } else if (user.getRegistration() == null) {
            sendMessage.setChatId(chatId);
            STATUSRU = "location";
            sendMessage.setText("Отправьте информацию о прописке");
        } else if (user.getPhoneNumber() == null) {
            STATUSRU = "phoneNumber";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Укажите номер телефона, привязанный к банковской карте");
        } else if (user.getFullTitle() == null) {
            STATUSRU = "fulTitle";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Напишите название требуемой мед услуги");
        } else if (user.getOperationCost() == null) {
            STATUSRU = "money";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Напишите стоимость операции в национальной валюте");
        } else if (user.getClinicName() == null) {
            STATUSRU = "nemeClinick";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Напишите название клиники");
        } else if (user.getCardPhoto() == null) {
            STATUSRU = "cardPhoto";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Отправьте фотографию банковской карты");
        } else if (user.getCardPhoto() != null && user.getPassportPhoto1() != null && user.getRegistration() != null
                && user.getPhoneNumber() != null && user.getFullTitle() != null && user.getOperationCost() != null
                && user.getClinicName() != null && user.getPercentage() == null && user.getFirstname() != null && user.getLastname() != null) {
            STATUSRU = null;
            sendMessage.setChatId(chatId);
            sendMessage.setText("Ваша информация отправлена администратору. Ожидайте ответа от администратора.");
        }
        execute(sendMessage);
    }

    public SendMessage step5Uz(long chatId) {
        User user = userRepository.findByChatId(chatId).get();
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
        dale.setText("▶\uFE0F Дальше.");
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
        User user = userRepository.findByChatId(chatId).get();
        if (update.hasCallbackQuery()) {
            sendMessage.setChatId(chatId);
            update.getCallbackQuery().getMessage().getText();
        } else if (user.getFirstname() == null) {
            STATUSUZ = "firstname";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Ismingizni kiriting");
        } else if (user.getLastname() == null) {
            STATUSUZ = "lastname";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Familiyangizni kiriting");
        } else if (user.getPassportPhoto1() == null) {
            STATUSUZ = "passportPhoto1";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Pasport yoki shaxsni tasdiqlovchi fotosurat (old)");
        } else if (user.getPassportPhoto2() == null) {
            STATUSUZ = "passportPhoto2";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Pasport yoki shaxsni tasdiqlovchi fotosurat (orqa)");
        } else if (user.getRegistration() == null) {
            STATUSUZ = "location";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Roʻyxatda turgan manzilingiz");
        } else if (user.getPhoneNumber() == null) {
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
        } else if (user.getCardPhoto() == null) {
            STATUSUZ = "cardPhoto";
            sendMessage.setChatId(chatId);
            sendMessage.setText("Ariza beruvchining plastik kartasi fotosurati");
        } else if (user.getCardPhoto() != null && user.getPassportPhoto1() != null && user.getRegistration() != null
                && user.getPhoneNumber() != null && user.getFullTitle() != null && user.getOperationCost() != null
                && user.getClinicName() != null && user.getFirstname() != null && user.getLastname() != null && user.getPercentage() == null) {
            STATUSUZ = null;
            sendMessage.setChatId(chatId);
            sendMessage.setText("Malumotlaringiz adminga yuborildi admindan javon kuting");
        }
        execute(sendMessage);
    }


    public SendMessage step5Ru(long chatId) {
        User user = userRepository.findByChatId(chatId).get();
        user.setSuccess("true");
        userRepository.save(user);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Выберите тариф");
        sendPhotoToTelegram(chatId, "", "src/main/resources/image/tarifRu.jpeg");
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

        File photoFile = new File(photoFilePath);
        try {
            if (photoFile.exists()) {
                InputFile photoInputFile = new InputFile(photoFile);
                sendPhoto.setPhoto(photoInputFile);
                execute(sendPhoto);
            } else {
                System.err.println("Photo file does not exist: " + photoFilePath);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
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
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
        var payment = new InlineKeyboardButton();
        payment.setText("Переотправить переднюю часть паспорта");
        payment.setCallbackData(PASSPORT_BUTTON);
        var back = new InlineKeyboardButton();
        back.setText("Потвердить паспорт");
        back.setCallbackData(NO_BUTTON);
        rowInLine.add(payment);
        rowInLine1.add(back);
        rowsInLine.add(rowInLine);
        rowsInLine.add(rowInLine1);
        markupInLine.setKeyboard(rowsInLine);
        sendMessage.setReplyMarkup(markupInLine);
        execute(sendMessage);
    }

    private void forwardPhotoToAdmin(Message message) throws TelegramApiException {
        ForwardMessage forwardMessage = new ForwardMessage();
        forwardMessage.setChatId(groupChatId);
        forwardMessage.setFromChatId(message.getChatId());
        forwardMessage.setMessageId(message.getMessageId());
        execute(forwardMessage);
        User user = userRepository.findByChatId(message.getChatId()).get();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(groupChatId);
        sendMessage.setText(user.getFirstname() + "    " + user.getLastname() + "\n" + user.getChatId());
        execute(sendMessage);
    }

    private void forwardPhotoToUser(Message message, String caption) throws TelegramApiException {
        ForwardMessage forwardMessage = new ForwardMessage();
        forwardMessage.setChatId(caption);
        forwardMessage.setFromChatId(groupChatId);
        forwardMessage.setMessageId(message.getMessageId());
        execute(forwardMessage);
    }

    @Override
    public String getBotUsername() {
        return "https://t.me/mmm_medical_bot";
    }

    @Override
    public String getBotToken() {
        return "6679898255:AAEOuYpimhr76-zEqUNFlMPo0UGkWrSiLPY";
    }


    private void loactionYes(long chatId, String text) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
        var payment = new InlineKeyboardButton();
        payment.setText("Переотправьте прописку");
        payment.setCallbackData(LOCATION);
        var back = new InlineKeyboardButton();
        back.setText("Принять прописку");
        back.setCallbackData(LOCATION_ACCEPT);
        rowInLine.add(payment);
        rowInLine1.add(back);
        rowsInLine.add(rowInLine);
        rowsInLine.add(rowInLine1);
        markupInLine.setKeyboard(rowsInLine);
        sendMessage.setReplyMarkup(markupInLine);
        execute(sendMessage);
    }

    private void phoneYes(long chatId, String text) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
        var payment = new InlineKeyboardButton();
        payment.setText("Переотправьте номер привязанный к карте");
        payment.setCallbackData(PHONE_BUTTON);
        var back = new InlineKeyboardButton();
        back.setText("Принять номер");
        back.setCallbackData(PHONE_BUTTON_ACCEPT);
        rowInLine.add(payment);
        rowInLine1.add(back);
        rowsInLine.add(rowInLine);
        rowsInLine.add(rowInLine1);
        markupInLine.setKeyboard(rowsInLine);
        sendMessage.setReplyMarkup(markupInLine);
        execute(sendMessage);
    }

    private void moneyYes(long chatId, String text) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
        var payment = new InlineKeyboardButton();
        payment.setText("Переотправьте сумму операции");
        payment.setCallbackData(PAYMENT);
        var back = new InlineKeyboardButton();
        back.setText("Принять сумму операции");
        back.setCallbackData(PAYMENT_ACCEPT);
        rowInLine.add(payment);
        rowInLine1.add(back);
        rowsInLine.add(rowInLine);
        rowsInLine.add(rowInLine1);
        markupInLine.setKeyboard(rowsInLine);
        sendMessage.setReplyMarkup(markupInLine);
        execute(sendMessage);
    }

    private void clinicYes(long chatId, String text) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
        var payment = new InlineKeyboardButton();
        payment.setText("Переотправьте название клиники");
        payment.setCallbackData(CLINIC);
        var back = new InlineKeyboardButton();
        back.setText("Принять название клиники");
        back.setCallbackData(CLINIC_ACCEPT);
        rowInLine.add(payment);
        rowInLine1.add(back);
        rowsInLine.add(rowInLine);
        rowsInLine.add(rowInLine1);
        markupInLine.setKeyboard(rowsInLine);
        sendMessage.setReplyMarkup(markupInLine);
        execute(sendMessage);
    }

    private void operationYes(long chatId, String text) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
        var payment = new InlineKeyboardButton();
        payment.setText("Переотправьте название операции");
        payment.setCallbackData(OPERATION);
        var back = new InlineKeyboardButton();
        back.setText("Принять название операции");
        back.setCallbackData(OPERATION_ACCEPT);
        rowInLine.add(payment);
        rowInLine1.add(back);
        rowsInLine.add(rowInLine);
        rowsInLine.add(rowInLine1);
        markupInLine.setKeyboard(rowsInLine);
        sendMessage.setReplyMarkup(markupInLine);
        execute(sendMessage);
    }


    private void pasportBack(long chatId, String text) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
        var payment = new InlineKeyboardButton();
        payment.setText("Переотправьте заднюю сторону пасспорта");
        payment.setCallbackData(PASSPORT_BUTTON_BACK);
        var back = new InlineKeyboardButton();
        back.setText("Принять заднюю сторону пасспорта");
        back.setCallbackData(NO_BUTTON_BACK);
        rowInLine.add(payment);
        rowInLine1.add(back);
        rowsInLine.add(rowInLine);
        rowsInLine.add(rowInLine1);
        markupInLine.setKeyboard(rowsInLine);
        sendMessage.setReplyMarkup(markupInLine);
        execute(sendMessage);
    }

    private void cardYes(long chatId, String text) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine2 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine3 = new ArrayList<>();
        var payment = new InlineKeyboardButton();
        payment.setText("Переотправьте фото пластиковой карты");
        payment.setCallbackData(CARD);
        var back = new InlineKeyboardButton();
        back.setText("Принять пластиковую карту");
        back.setCallbackData(CARDA);
        var accept = new InlineKeyboardButton();
        accept.setText("✅ Принять все документы");
        accept.setCallbackData(ACCEPT_BUTTON);
        var reject = new InlineKeyboardButton();
        reject.setText("❌ Отказать в кредите");
        reject.setCallbackData(REJECT);
        rowInLine2.add(payment);
        rowInLine1.add(back);
        rowInLine3.add(accept);
        rowInLine3.add(reject);
        rowsInLine.add(rowInLine2);
        rowsInLine.add(rowInLine1);
        rowsInLine.add(rowInLine3);
        markupInLine.setKeyboard(rowsInLine);
        sendMessage.setReplyMarkup(markupInLine);
        execute(sendMessage);
    }

    public SendMessage newStepRu(Message message) {
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
        return sendMessage;
    }

    public SendMessage newStepUz(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(conditionUz2());
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

    public String conditionUz() {
        return "Toʻlov rejasini soʻrash uchun “Keyingisi” tugmasini bosing. Diqqat! “Keyingisi” tugmasini bosish orqali siz shaxsiy ma’lumotlaringizga ishlov berishga rozilik bildirasiz";
    }

    private String conditionUz2() {
        return "Arizani to'ldirish uchun siz ba'zi hujjatlarni taqdim etishingiz kerak. To'lov rejasini rad etishning asosiy sabablarini ko'rib chiqing:\n" +
                "• Yosh chegarasi: 18 yoshdan 60 yoshgacha.\n" +
                "• Salbiy kredit tarixiga ega bo'lish\n" +
                "• Kredit qarzi\n" +
                "• BPI (MIB)dagi qarz\n" +
                "• Oxirgi 4 oy ichida tushumlar past.";
    }

    public String conditionRu() {
        return "Чтобы запросить рассрочку нажмите «ПРОДОЛЖИТЬ». Внимание! Нажимая «ПРОДОЛЖИТЬ», вы даете согласие на обработку своих персональных данных";

    }

    private String conditionRu2() {
        return "Для оформления заявки необходимо предоставить некоторые документы. Ознакомьтесь с основными причинами получения отказа в рассрочке: \n" +
                "• Возрастное ограничение: от 18 до 60 лет. \n" +
                "• Наличие отрицательной кредитной истории \n" +
                "• Кредитная задолженность \n" +
                "• Долг в БПИ (MIB) \n" +
                "• За последние 4 месяца поступления были низкими";
    }
}
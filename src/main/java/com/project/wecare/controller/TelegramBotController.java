package com.project.wecare.controller;

import com.project.wecare.repository.UserRepository;
import jakarta.ws.rs.OPTIONS;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.http.cookie.SM;
import org.apache.http.util.NetUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.project.wecare.entity.User;


@Component
@RequiredArgsConstructor
public class TelegramBotController extends TelegramLongPollingBot {

    private final UserRepository userRepository;
    long groupChatId = -997375479;
    boolean response = false;
    private String YES_BUTTON = "Yes";
    private String NO_BUTTON = "NO";
    private long userId;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        SendMessage returnMassage = new SendMessage();
       // Message message = update.getMessage();

        System.out.println(update.getMessage().getChatId());

        if (update.getMessage().hasPhoto()){
            System.out.println("photo keldi \n\n\n\n \n\n\n");
            ForwardMessage forwardMessage=new ForwardMessage();
            forwardMessage.setChatId(groupChatId);
            forwardMessage.setFromChatId(update.getMessage().getChatId());
            forwardMessage.setMessageId(update.getMessage().getMessageId());

            execute(forwardMessage);
        }

 if (update.getMessage().hasText()) {
     if (update.getMessage().getText().equals("/start")) {
         returnMassage = buttonStart(update.getMessage());
     }

     if (update.getMessage().getText().equals("\uD83C\uDDFA\uD83C\uDDFF Uz")) {
         returnMassage = uz(update.getMessage());
     }
     if (update.getMessage().getText().equals("\uD83C\uDDF7\uD83C\uDDFA Ru")) {
         returnMassage = ru(update.getMessage());
     }
     if (update.getMessage().getText().equals("◀️ Ortga qaytish") || update.getMessage().getText().equals("◀️ Назад")) {
         returnMassage = buttonStart(update.getMessage());
     }
     if (update.getMessage().getText().equals("▶\uFE0F Keyingisi")) {
            /*if (update.getMessage().getChatId() != groupChatId || update.getCallbackQuery().getMessage().getChatId() != groupChatId) {
                userId = update.getMessage().getChatId();
            }*/
         returnMassage = step4Uz(update);

     }
     if (update.getMessage().getText().equals("▶\uFE0F Keyingisi.")) {
         returnMassage = step5(update);
     }
     if (update.getMessage().getText().equals("◀️ Ortga qaytish..")) {
         returnMassage = step4Uz(update);
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

    @Override
    public String getBotUsername() {
        return "https://t.me/mmm_medical_bot";
    }

    @Override
    public String getBotToken() {
        return "6679898255:AAEOuYpimhr76-zEqUNFlMPo0UGkWrSiLPY";
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

    public SendMessage step4Ru(Update update, Message message) {

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
    public SendMessage step5(Update update) {
        long chatId = update.getMessage().getChatId();
        //  sendPhotos.photo(update);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText("Tarifni tanglang:");
        sendPhotoToTelegram(chatId, "", "src/main/resources/image/img.png");
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(createInlineKeyboardRow("3 мес -20%", "tariff_3_months"));
        keyboard.add(createInlineKeyboardRow("6 мес -25%", "tariff_6_months"));
        keyboard.add(createInlineKeyboardRow("9 мес - 32%", "tariff_9_months"));
        keyboard.add(createInlineKeyboardRow("12 мес - 38%", "tariff_12_months"));
        keyboardMarkup.setKeyboard(keyboard);
        sendMessage.setChatId(update.getMessage().getChatId());
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

    public SendMessage step4Uz(Update update) {
        long chatId = update.getMessage().getChatId();
        CallbackQuery callbackQuery = new CallbackQuery();
        if (update.hasCallbackQuery()) {
            callbackQuery = update.getCallbackQuery();
        }

        Message message = new Message();

        if (update.hasMessage()) {
            message = update.getMessage();
        }

        register(update.getMessage());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        Optional<User> optional = userRepository.findByChatId(chatId);

        String response = "";
        if (update.hasCallbackQuery()) {
            update.getCallbackQuery().getMessage().getText();
        }
        User user = optional.get();
        if (user.getPassportPhoto() == null && response.equals("Ha") && message.getPhoto() != null
                || user.getPassportPhoto() == null && response.equals("Ha") && callbackQuery.getMessage().getPhoto() != null) {
            User saveUser = userRepository.findByChatId(userId).get();
            File file = new File(update.getMessage().getText());
            if (update.getMessage().getPhoto() != null) {
                file = (File) update.getMessage().getPhoto();
            }
            if (update.getCallbackQuery().getMessage().getPhoto() != null) {
                file = (File) update.getCallbackQuery().getMessage().getPhoto();
            }
            if (user.getPassportPhoto() == null) {
                sendPhotoToTelegram(groupChatId, "asdadad", file.getPath());
            }

            saveUser.setPassportPhoto(file);
            sendMessage.setText("password was saved");
        }

        if (user.getPassportPhoto() == null) {
            sendMessage.setText("1. Фото паспорта или ID (передняя и задняя сторона)");
            sendMessage.setChatId(chatId);


        }


        return sendMessage;
    }

    public SendMessage step5(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Step 4");
        sendMessage.setChatId(message.getChatId());
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

    private void register(Message message) {
        long chatId = message.getChatId();
        User user = new User();
        user.setChatId(chatId);
        if (message.getChat().getUserName() != null) {
            user.setUsername(message.getChat().getUserName());
        }
        userRepository.save(user);
    }

    private SendMessage YesNoAdmin(long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var payment = new InlineKeyboardButton();

        payment.setText("Ha");
        payment.setCallbackData(YES_BUTTON);

        var back = new InlineKeyboardButton();

        back.setText("Yoq");
        back.setCallbackData(NO_BUTTON);

        rowInLine.add(payment);
        rowInLine.add(back);

        rowsInLine.add(rowInLine);

        markupInLine.setKeyboard(rowsInLine);
        sendMessage.setReplyMarkup(markupInLine);
        return sendMessage;
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

}

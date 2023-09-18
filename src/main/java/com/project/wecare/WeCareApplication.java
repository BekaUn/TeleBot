package com.project.wecare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
public class WeCareApplication  {
    public static void main(String[] args) throws TelegramApiException {
//multithreading1.start();
SpringApplication.run(WeCareApplication.class, args);
        //}
        //     throw new TelegramApiException();
        // SpringApplication.run(WeCareApplication.class, args);
    }
}
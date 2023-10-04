package com.project.wecare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
public class WeCareApplication  {
    public static void main(String[] args) throws TelegramApiException {

SpringApplication.run(WeCareApplication.class, args);

    }
}
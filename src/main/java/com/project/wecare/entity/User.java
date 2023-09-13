package com.project.wecare.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Table(name = ("data"))
@Entity
public class User {
    @Id
    private Long chatId;
    private String firstname;
    private String lastname;
    private String username;
    private String passportPhoto1;
    private String passportPhoto2;
    private String registration;
    private String cardPhoto;



    private String phoneNumber;
    private String fullTitle;
    private String percentage;
    private String operationCost;
    private String clinicName;
    private String success;

    @Override
    public String toString() {
        return "Клиент" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", registration='" + registration + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", fullTitle='" + fullTitle + '\'' +
                ", percentage='" + percentage + '\'' +
                ", operationCost='" + operationCost + '\'' +
                ", clinicName='" + clinicName + '\'' +
                '}';
    }
}

package com.project.wecare.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

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
    private String username;
    private String passportPhoto;
    private String registration;
    private String cardPhoto;
    private String phoneNumber;
    private String fullTitle;
    private String percentage;
    private Double operationCost;
    private String clinicName;
}

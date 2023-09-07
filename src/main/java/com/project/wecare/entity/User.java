package com.project.wecare.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
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
    private PhotoSize passportPhoto;
    private String registration;
    private File cardPhoto;
    private String phoneNumber;
    private String fullTitle;
    private String percentage;
    private Double operationCost;
    private String clinicName;
}

package com.project.wecare.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
@Table(name = ("data"))
@Entity
public class Data {
    @Id
    private Long chatId;
    private File passportPhoto;
    private String registration;
    private File cardPhoto;
    private String phoneNumber;
    private String fullTitle;
    private Double operationCost;
    private String clinicName;
}

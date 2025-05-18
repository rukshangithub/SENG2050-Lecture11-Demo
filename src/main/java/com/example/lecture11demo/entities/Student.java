package com.example.lecture11demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student")
public class Student {

    @Id
    @Column(name = "stdNo")
    private String stdNo;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "givennames")
    private String givenNames;

    @Column(name = "passwordhash")
    private String passwordHash;

    @Column(name = "passwordsalt")
    private Double passwordSalt;

}



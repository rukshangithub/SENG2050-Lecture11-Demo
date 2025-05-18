package com.example.lecture11demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Course")
public class Course {

    @Id
    @Column(name = "courseID")
    private String courseId;

    @Column(name = "cname")
    private String courseName;

    @Column(name = "credits")
    private Integer credit;
}


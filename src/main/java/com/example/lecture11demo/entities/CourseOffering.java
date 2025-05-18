package com.example.lecture11demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Entity
@Table(name = "courseofferings")
public class CourseOffering {

    @EmbeddedId
    private CourseOfferingId id;

    @ManyToOne
    @MapsId("semesterId")
    @JoinColumn(name = "semesterID")
    private Semester semester;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "courseID")
    private Course course;


    @Column(name = "maxcapacity")
    private Integer maxCapactity;

    public CourseOffering()
    {
        this.id = new CourseOfferingId();
        this.semester = new Semester();
        this.course = new Course();
        this.maxCapactity = 0;
    }
}


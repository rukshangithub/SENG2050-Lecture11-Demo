package com.example.lecture11demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lecture11demo.entities.CourseOffering;
import com.example.lecture11demo.entities.CourseOfferingId;

public interface CourseOfferingRepository extends JpaRepository<CourseOffering, CourseOfferingId>{

}

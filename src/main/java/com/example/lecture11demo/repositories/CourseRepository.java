package com.example.lecture11demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.lecture11demo.entities.Course;

public interface CourseRepository extends JpaRepository<Course, String> {

}

package com.example.lecture11demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.lecture11demo.entities.Semester;

public interface SemesterRepository extends JpaRepository<Semester, Integer> {
}

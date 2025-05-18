package com.example.lecture11demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.lecture11demo.entities.Course;
import com.example.lecture11demo.entities.CourseOffering;
import com.example.lecture11demo.entities.CourseOfferingId;
import com.example.lecture11demo.entities.Semester;
import com.example.lecture11demo.entities.StudentCourseRegistration;
import com.example.lecture11demo.repositories.CourseOfferingRepository;
import com.example.lecture11demo.repositories.CourseRepository;
import com.example.lecture11demo.repositories.SemesterRepository;


@SpringBootApplication
public class Lecture11demoApplication {

	public static void main(String[] args) {
		SpringApplication.run(Lecture11demoApplication.class, args);
		
		// ApplicationContext context = SpringApplication.run(Lecture11demoApplication.class, args);

		// var courseOfferingRepository = context.getBean(CourseOfferingRepository.class);
		// var semesterRepository = context.getBean(SemesterRepository.class);
		// var courseRepository = context.getBean(CourseRepository.class);

		// var semester = semesterRepository.findById(Integer.valueOf(102)).orElse(null);


		// var course = courseRepository.findById(new String("COMP1140")).orElse(null);

		// var courseOffering = new CourseOffering();
		// courseOffering.setId(new CourseOfferingId(102,"COMP1140"));
		// courseOffering.setCourse(course);
		// courseOffering.setSemester(semester);
		// courseOffering.setMaxCapactity(10);


		//courseOfferingRepository.save(courseOffering); //Saves courseOffering
	}

}

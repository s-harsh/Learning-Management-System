package com.example.test2.repositories;

import com.example.test2.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//import java.util.List;


@Repository
public interface courseRepo extends JpaRepository<Course,Long> {

    List<Course> findByCategory(String category);
//    List<Course> findByCourseNameContainingIgnoreCase(String keyword);
//    List<Course> findByCategoryAndCourseNameContainingIgnoreCase(String category, String keyword);

}

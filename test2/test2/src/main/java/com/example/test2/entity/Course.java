package com.example.test2.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "newcourse")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long courseId;

    @Column(name = "courseName")
    private String courseName;

    @Column(name = "courseTime")
    private String courseTime;

    @Column(name = "Category")
    private String category;

    private String description;


    public Course(long courseId, String courseName, String courseDuration, String category, String description) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseTime = courseDuration;
        this.category = category;
        this.description = description;
    }

    public Course() {
    }

    @Override
    public String toString() {
        return "course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", courseDuration='" + courseTime + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


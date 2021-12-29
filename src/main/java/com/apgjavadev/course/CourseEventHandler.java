package com.apgjavadev.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RepositoryEventHandler
public class CourseEventHandler {

  private final CourseRepository courses;

  @Autowired
  public CourseEventHandler(CourseRepository courses) {
    this.courses = courses;
  }

  @HandleBeforeCreate
  public void checkThatCourseDoesNotAlreadyExistByUrl(Course course) {
    String url = course.getUrl();
    if (courses.findByUrl(url).isPresent()) {
  // TODO: send appropriate response client
      throw new RuntimeException("Course is already present in DB");
    }
  }
}

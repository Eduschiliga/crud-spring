package com.eduardo.dto.mapper;

import com.eduardo.dto.CourseDTO;
import com.eduardo.dto.LessonDTO;
import com.eduardo.enums.Category;
import com.eduardo.model.Course;
import com.eduardo.model.Lesson;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseMapper {
  public CourseDTO toDTO(Course course) {
    if (course == null) {
      return null;
    }

    List<LessonDTO> lessonDTOs = course.getLessons()
        .stream()
        .map(lesson -> new LessonDTO(lesson.getId(), lesson.getName(), lesson.getYoutubeUrl()))
        .toList();

    return new CourseDTO(
        course.getId(),
        course.getName(),
        course.getCategory().getValue(),
        lessonDTOs
    );
  }

  public Course toEntity(CourseDTO courseDTO) {
    if (courseDTO == null) {
      return null;
    }

    Course course = new Course();

    if (courseDTO.id() != null) {
      course.setId(courseDTO.id());
    }

    course.setName(courseDTO.name());
    course.setCategory(convertCategory(courseDTO.category()));

    List<Lesson> lessons = courseDTO.lessons().stream().map(lessonDTO -> {
      var lesson = new Lesson();
      lesson.setId(lessonDTO.id());
      lesson.setName(lessonDTO.name());
      lesson.setYoutubeUrl(lessonDTO.youtubeUrl());
      lesson.setCourse(course);
      return lesson;
    }).toList();

    course.setLessons(lessons);

    return course;
  }

  public Category convertCategory(String value) {
    if (value == null) {
      return null;
    }

    return switch (value) {
      case "Front-End" -> Category.FRONT_END;
      case "Back-End" -> Category.BACK_END;
      default -> throw new IllegalArgumentException("Invalid category: " + value);
    };
  }
}

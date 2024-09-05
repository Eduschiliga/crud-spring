package com.eduardo.service;

import com.eduardo.dto.CourseDTO;
import com.eduardo.dto.CoursePageDTO;
import com.eduardo.dto.mapper.CourseMapper;
import com.eduardo.exceptions.RecordNotFoundException;
import com.eduardo.model.Course;
import com.eduardo.repository.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Service
public class CourseService {
  private final CourseRepository courseRepository;
  private final CourseMapper courseMapper;

  public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
    this.courseRepository = courseRepository;
    this.courseMapper = courseMapper;
  }

  public CoursePageDTO list(@PositiveOrZero int pageNumber, @Positive @Max(100) int size) {
    Page<Course> page = courseRepository.findAll(PageRequest.of(pageNumber, size));
    List<CourseDTO> courseDTOS = page.get().map(courseMapper::toDTO).toList();
    return new CoursePageDTO(courseDTOS, page.getTotalElements(), page.getTotalPages());
  }

  public CourseDTO findById(@NotNull @Positive Long id) {
    return courseRepository.findById(id).map(courseMapper::toDTO).orElseThrow(() -> new RecordNotFoundException(id));
  }

  public CourseDTO create(@Valid @NotNull CourseDTO course) {
    return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(course)));
  }

  public CourseDTO update(@Valid @NotNull @Positive Long id, @Valid CourseDTO courseDTO) {
    return courseRepository.findById(id).map(recordFound -> {
      Course course = courseMapper.toEntity(courseDTO);
      recordFound.setName(courseDTO.name());
      recordFound.setCategory(courseMapper.convertCategory(courseDTO.category()));
      recordFound.getLessons().clear();
      course.getLessons().forEach(lessonFound -> recordFound.getLessons().add(lessonFound));
      return courseMapper.toDTO(courseRepository.save(recordFound));
    }).orElseThrow(() -> new RecordNotFoundException(id));
  }

  public void delete(@NotNull @Valid @Positive Long id) {
    courseRepository.delete(courseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
  }
}

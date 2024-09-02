package com.eduardo.service;

import com.eduardo.dto.CourseDTO;
import com.eduardo.dto.mapper.CourseMapper;
import com.eduardo.exceptions.RecordNotFoundException;
import com.eduardo.repository.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Validated
@Service
public class CourseService {
  private final CourseRepository courseRepository;
  private final CourseMapper courseMapper;

  public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
    this.courseRepository = courseRepository;
    this.courseMapper = courseMapper;
  }

  public List<CourseDTO> list() {
    return courseRepository.findAll().stream().map(courseMapper::toDTO).collect(Collectors.toList());
  }

  public CourseDTO findById(@PathVariable @NotNull @Positive Long id) {
    return courseRepository.findById(id).map(courseMapper::toDTO).orElseThrow(() -> new RecordNotFoundException(id));
  }

  public CourseDTO create(@Valid @NotNull CourseDTO course) {
    return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(course)));
  }

  public CourseDTO update(@Valid @NotNull @Positive Long id, @Valid CourseDTO course) {
    return courseRepository.findById(id).map(recordFound -> {
      recordFound.setName(course.name());
      recordFound.setCategory(course.category());
      return courseMapper.toDTO(courseRepository.save(recordFound));
    }).orElseThrow(() -> new RecordNotFoundException(id));
  }

  public void delete(@PathVariable @NotNull @Valid @Positive Long id) {
    courseRepository.delete(courseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
  }
}

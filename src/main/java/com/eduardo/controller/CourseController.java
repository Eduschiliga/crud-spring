package com.eduardo.controller;

import com.eduardo.dto.CourseDTO;
import com.eduardo.dto.CoursePageDTO;
import com.eduardo.service.CourseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/courses")
public class CourseController {

  private final CourseService courseService;

  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  @GetMapping
  public CoursePageDTO list(@RequestParam(defaultValue = "0") @PositiveOrZero int page, @RequestParam(defaultValue = "10") @Positive @Max(100) int pageSize) {
    return courseService.list(page, pageSize);
  }

  @GetMapping("/{id}")
  public CourseDTO findById(@PathVariable @NotNull @Valid @Positive Long id) {
    return courseService.findById(id);
  }

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public CourseDTO create(@RequestBody @Valid CourseDTO course) {
    return courseService.create(course);
  }

  @PutMapping("/{id}")
  public CourseDTO update(@PathVariable @Valid @NotNull @Positive Long id, @RequestBody @Valid CourseDTO course) {
    return courseService.update(id, course);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void delete(@PathVariable @NotNull @Valid @Positive Long id) {
    courseService.delete(id);
  }
}

package com.eduardo.dto;

import java.util.List;

public record CoursePageDTO(
    List<CourseDTO> content,
    long totalElements,
    int totalPages) {
}

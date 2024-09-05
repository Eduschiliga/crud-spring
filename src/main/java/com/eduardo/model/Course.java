package com.eduardo.model;

import com.eduardo.enums.Category;
import com.eduardo.enums.Status;
import com.eduardo.enums.converters.CategoryConverter;
import com.eduardo.enums.converters.StatusConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@SQLDelete(sql = "UPDATE Course SET status = 'Inative' WHERE id = ?")
@Where(clause = "status = 'Active'")
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonProperty("_id")
  private Long id;

  @NotBlank
  @NotNull
  @Length(min = 5, max = 100)
  @Column(length = 100, nullable = false)
  private String name;

  @NotNull
  @Column(length = 10, nullable = false)
  @Convert(converter = CategoryConverter.class)
  private Category category;

  @NotNull
  @Column(length = 10, nullable = false)
  @Convert(converter = StatusConverter.class)
  private Status status = Status.ACTIVE;

  @NotNull
  @NotEmpty
  @Valid
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "course")
  private List<Lesson> lessons = new ArrayList<>();
}

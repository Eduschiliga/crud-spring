package com.eduardo;

import com.eduardo.enums.Category;
import com.eduardo.model.Course;
import com.eduardo.model.Lesson;
import com.eduardo.repository.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrudSpringApplication {

  public static void main(String[] args) {
    SpringApplication.run(CrudSpringApplication.class, args);
  }

  @Bean
  CommandLineRunner initDatabase(CourseRepository courseRepository) {
    return args -> {
      courseRepository.deleteAll();

      Course c = new Course();
      c.setName("Angular + Spring");
      c.setCategory(Category.BACK_END);

      Lesson l1 = new Lesson();
      l1.setName("Introduction I");
      l1.setYoutubeUrl("https://www.youtube.com/embed/");
      l1.setCourse(c);
      c.getLessons().add(l1);

      Lesson l2 = new Lesson();
      l2.setName("Introduction II");
      l2.setYoutubeUrl("https://www.youtube.com/embed/");
      l2.setCourse(c);
      c.getLessons().add(l2);

      courseRepository.save(c);
    };
  }
}

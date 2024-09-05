package com.eduardo;

import com.eduardo.enums.Category;
import com.eduardo.model.Course;
import com.eduardo.model.Lesson;
import com.eduardo.repository.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class CrudSpringApplication {

  public static void main(String[] args) {
    SpringApplication.run(CrudSpringApplication.class, args);
  }

  @Bean
  @Profile("dev")
  CommandLineRunner initDatabase(CourseRepository courseRepository) {
    return args -> {
      courseRepository.deleteAll();

      for (int i = 1; i <= 150; i += 1) {
        Course c = new Course();
        c.setName("Angular + Spring " + i);
        c.setCategory(Category.BACK_END);

        Lesson l1 = new Lesson();
        l1.setName("Introduction " + i);
        l1.setYoutubeUrl("12345678911");
        l1.setCourse(c);
        c.getLessons().add(l1);

        Lesson l2 = new Lesson();
        l2.setName("Intermediary " + i);
        l2.setYoutubeUrl("12345678911");
        l2.setCourse(c);
        c.getLessons().add(l2);

        Lesson l3 = new Lesson();
        l3.setName("Advanced " + i);
        l3.setYoutubeUrl("12345678911");
        l3.setCourse(c);
        c.getLessons().add(l3);

        courseRepository.save(c);
      }
    };
  }
}

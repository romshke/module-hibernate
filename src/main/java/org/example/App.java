package org.example;

import org.example.config.HibernateConfiguration;
import org.example.model.Profile;
import org.example.model.Student;
import org.example.service.ProfileService;
import org.example.service.StudentService;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(HibernateConfiguration.class);

        SessionFactory sessionFactory = context.getBean(SessionFactory.class);
        StudentService studentService = context.getBean(StudentService.class);
        ProfileService profileService = context.getBean(ProfileService.class);

        Student student1 = new Student("Ivan", 20);
        Student student2 = new Student("Oleg", 30);

        studentService.saveStudent(student1);
        studentService.saveStudent(student2);

        Profile profile1 = new Profile("My bio", LocalDateTime.now(), student1);

        profileService.saveProfile(profile1);

    }
}

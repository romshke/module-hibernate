package org.example;

import org.example.config.HibernateConfiguration;
import org.example.model.Group;
import org.example.model.Profile;
import org.example.model.Student;
import org.example.service.GroupService;
import org.example.service.ProfileService;
import org.example.service.StudentService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(HibernateConfiguration.class);

        SessionFactory sessionFactory = context.getBean(SessionFactory.class);
        StudentService studentService = context.getBean(StudentService.class);
        ProfileService profileService = context.getBean(ProfileService.class);
        GroupService groupService = context.getBean(GroupService.class);

        Group group1 = groupService.saveGroup("1", 2025L);
        Group group2 = groupService.saveGroup("2", 2025L);
        Group group3 = groupService.saveGroup("3", 2025L);

        Student student1 = new Student("Ivan", 20, group1);
        Student student2 = new Student("Oleg", 30, group1);

        studentService.saveStudent(student1);
        studentService.saveStudent(student2);

        System.out.println();
        System.out.println();
        System.out.println("____________________________________________________________");

        groupService.findAll();
    }
}

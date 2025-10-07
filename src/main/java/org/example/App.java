package org.example;

import org.example.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("org.example");

        SessionFactory sessionFactory = context.getBean(SessionFactory.class);

        Session session = sessionFactory.openSession();

        Student student1 = new Student("Ivan", 20);
        Student student2 = new Student("Oleg", 30);

        session.beginTransaction();
        session.persist(student1);
        session.persist(student2);
        session.getTransaction().commit();

        Student student1ById = session.find(Student.class, 1);
        System.out.println(student1ById);

        Student student2ById = session
                .createQuery("SELECT s from Student s where s.id = :id", Student.class)
                .setParameter("id", 2)
                .getSingleResult();
        System.out.println(student2ById);

        session.beginTransaction();
        Student studentForUpdate = session.find(Student.class, 1);
        studentForUpdate.setAge(25);
        studentForUpdate.setName("Ivan Petrov");
        session.getTransaction().commit();

        session.beginTransaction();
        Student studentForDelete = session.find(Student.class, 2);
//        session.remove(studentForDelete);
//        session.createQuery("DELETE FROM Student s WHERE s.id = 2")
//                .executeUpdate();
//        session.createNativeQuery("DELETE FROM students WHERE id = 2")
//                .executeUpdate();
        session.getTransaction().commit();

        List<Student> students = session
                .createQuery("SELECT s FROM Student s", Student.class)
//                .getResultList();
                .list();

        students.forEach(System.out::println);

        Student studentByName = session
                .createQuery("SELECT s FROM Student s WHERE s.name = :name", Student.class)
                .setParameter("name", "Ivan Petrov")
                .getSingleResult();

        System.out.println(studentByName);

        Student student3 = new Student(null, 22);
        Student student4 = new Student("Oleg", 22);

        session.beginTransaction();
//        session.persist(student3);
        session.persist(student4);
        session.getTransaction().commit();

        session.close();
    }
}

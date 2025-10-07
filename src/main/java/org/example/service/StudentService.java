package org.example.service;

import org.example.model.Student;
import org.example.util.TransactionUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final SessionFactory sessionFactory;
    private final TransactionUtil transactionUtil;

    public StudentService(SessionFactory sessionFactory, TransactionUtil transactionUtil) {
        this.sessionFactory = sessionFactory;
        this.transactionUtil = transactionUtil;
    }

    public Student saveStudent(Student student) {
        return transactionUtil.executeInTransaction(session -> {
            session.persist(student);
            return student;
        });
    }

    public void deleteStudent(Long id) {
        transactionUtil.executeInTransaction(session -> {
            Student student = session.find(Student.class, id);
            session.remove(student);
        });
    }

    public Student getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(Student.class, id);
        }
    }

    public List<Student> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select s from Student s", Student.class).getResultList();
        }
    }

    public Student updateStudent(Student student) {
        return transactionUtil.executeInTransaction(session -> {
            return session.merge(student);
        });
    }
}

package org.example.service;

import org.example.model.Course;
import org.example.util.TransactionUtil;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    private final SessionFactory sessionFactory;
    private final TransactionUtil transactionUtil;

    public CourseService(SessionFactory sessionFactory, TransactionUtil transactionUtil) {
        this.sessionFactory = sessionFactory;
        this.transactionUtil = transactionUtil;
    }

    public Course saveCourse(Course course) {
        return transactionUtil.executeInTransaction(session -> {
            session.persist(course);
            return course;
        });
    }

    public void enrollStudentToCourse(Long studentId, Long courseId) {
        transactionUtil.executeInTransaction(session -> {
//            var student = session.find(Student.class, studentId);
//            var course = session.find(Course.class, courseId);
//            student.getCourseList().add(course);
            String sql = """
                INSERT INTO student_courses (student_id, course_id)
                VALUES (:studentId, :courseId);
            """;

            session.createNativeQuery(sql, Void.class)
                    .setParameter("studentId", studentId)
                    .setParameter("courseId", courseId)
                    .executeUpdate();
        });
    }
}

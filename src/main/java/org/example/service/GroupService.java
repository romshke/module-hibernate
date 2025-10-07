package org.example.service;

import org.example.model.Group;
import org.example.util.TransactionUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final SessionFactory sessionFactory;
    private final TransactionUtil transactionUtil;

    public GroupService(SessionFactory sessionFactory, TransactionUtil transactionUtil) {
        this.sessionFactory = sessionFactory;
        this.transactionUtil = transactionUtil;
    }

    public Group saveGroup(String number, Long graduationYear) {
        return transactionUtil.executeInTransaction(session -> {
            var group = new Group(number, graduationYear);
            session.persist(group);
            return group;
        });
    }

    public List<Group> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("""
                SELECT g FROM Group g
                LEFT JOIN FETCH g.studentList s
                LEFT JOIN FETCH s.profile
            """,Group.class).list();
        }
    }
}

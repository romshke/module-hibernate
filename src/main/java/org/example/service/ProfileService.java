package org.example.service;

import org.example.model.Profile;
import org.example.util.TransactionUtil;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private final SessionFactory sessionFactory;
    private final TransactionUtil transactionUtil;

    public ProfileService(SessionFactory sessionFactory, TransactionUtil transactionUtil) {
        this.sessionFactory = sessionFactory;
        this.transactionUtil = transactionUtil;
    }

    public Profile saveProfile(Profile profile) {
        return transactionUtil.executeInTransaction(session -> {
            session.persist(profile);
            return profile;
        });
    }
}

package com.example.gymweb.Repositories;

import com.example.gymweb.Entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepositoryImplem {
    @PersistenceContext
    private EntityManager entityManager;

    public List<User> findByPhoneNumberAndEmail(int phoneNumber, String email){
        TypedQuery<User> query= entityManager.createQuery
                ("SELECT u FROM User u WHERE u.phoneNumber=:phoneNumber AND u.email=:email",User.class);
        return query.setParameter("phoneNumber",phoneNumber).setParameter("email",email).getResultList();
    }
    public List<User> findByEmails(String email){
        TypedQuery<User> query= entityManager.createQuery
                ("SELECT u FROM User u WHERE u.email=:email",User.class);
        return query.setParameter("email",email).getResultList();
    }
}

package com.example.gymweb.Repositories;


import com.example.gymweb.Entities.Ranking;
import com.example.gymweb.Entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RankingRepositoryImplem {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Ranking> findRankingByUser(User user){
        TypedQuery<Ranking> query=entityManager.createQuery("SELECT d FROM Ranking d WHERE d.user=:user",Ranking.class);
        return query.setParameter("user",user).getResultList();
    }



}

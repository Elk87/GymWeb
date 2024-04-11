package com.example.gymweb.Repositories;


import com.example.gymweb.Entities.Ranking;
import com.example.gymweb.Entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankingRepository extends JpaRepository<Ranking,Long> {
    List<Ranking> findRankingByUser(User user);
   Ranking findRankingById(Long id);
   @Transactional
   void deleteById(Long id);
}



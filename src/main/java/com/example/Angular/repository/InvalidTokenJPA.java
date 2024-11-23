package com.example.Angular.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.example.Angular.Entity.InvalidToken;

@Repository
public interface InvalidTokenJPA extends JpaRepository<InvalidToken, Integer> {
    Optional<InvalidToken> findByToken(String token);
}
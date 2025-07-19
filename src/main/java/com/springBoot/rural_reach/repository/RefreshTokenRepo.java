package com.springBoot.rural_reach.repository;

import com.springBoot.rural_reach.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Integer> {
    /**
     * @param: token-> Previous Generated Refresh token
     * @return: Returns the token if found or return null
     */
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser_id(Long id);
}
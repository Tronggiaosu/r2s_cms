package com.example.CMSR2S.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CMSR2S.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    public boolean existsByUsername(String username);

    Optional<Member> findByUsername(String username);

    Optional<Member> findByEmail(String email);
}

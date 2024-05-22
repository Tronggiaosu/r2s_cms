package com.example.CMSR2S.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.CMSR2S.entity.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
    @Query("SELECT c FROM Content c WHERE member.id = :memberId")
    Page<Content> findAll(Long memberId, Pageable pageable);

    @Query("SELECT c FROM Content c WHERE member.id = :memberId AND c.id = :id")
    Content findContentById(Long memberId, Long id);

    @Query("SELECT c FROM Content c WHERE member.id = :memberId AND (c.frontpager LIKE %:keyword% OR c.brief LIKE %:keyword% OR c.content LIKE %:keyword%)")
    Page<Content> findContentByKeyword(Long memberId, @Param("keyword") String keyword,
            Pageable pageable);
}

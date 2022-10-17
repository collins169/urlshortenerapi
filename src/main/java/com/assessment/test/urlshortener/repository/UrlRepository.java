package com.assessment.test.urlshortener.repository;

import com.assessment.test.urlshortener.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByLongUrlAndExpiresDateIsLessThan(@NonNull String longUrl, LocalDateTime expiresDate);
}
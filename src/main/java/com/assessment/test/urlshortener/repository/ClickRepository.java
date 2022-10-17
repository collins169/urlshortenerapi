package com.assessment.test.urlshortener.repository;

import com.assessment.test.urlshortener.entity.Click;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface ClickRepository extends JpaRepository<Click, Long> {
    boolean existsByUrl_LongUrlAndIpAddress(@NonNull String longUrl, @NonNull String ipAddress);
}
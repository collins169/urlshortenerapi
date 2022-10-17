package com.assessment.test.urlshortener.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Table(name = "url")
@Entity
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String longUrl;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    private LocalDateTime expiresDate;
}
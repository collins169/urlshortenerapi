package com.assessment.test.urlshortener.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Table(name = "clicks")
@Entity
public class Click {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ipAddress;
    @OneToMany
    @JoinColumn(name = "url_id")
    private Url url;
}
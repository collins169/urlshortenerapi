package com.assessment.test.urlshortener.service;

import com.assessment.test.urlshortener.dto.UrlRequest;
import com.assessment.test.urlshortener.entity.Url;
import com.assessment.test.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private BaseEncryptor encryptor;

    public String convertToShortUrl(UrlRequest request) {
        Url url = new Url();
        url.setLongUrl(request.getLongUrl());
        url.setExpiresDate(request.getExpiresDate());
        url.setCreatedDate(LocalDateTime.now());
        Url entity = urlRepository.save(url);

        return encryptor.encode(entity.getId());
    }

    public String getOriginalUrl(String shortUrl) {
        Long id = encryptor.decode(shortUrl);
        Url entity = urlRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no entity with " + shortUrl));

        if ( Objects.nonNull(entity.getExpiresDate()) && entity.getExpiresDate().isBefore(LocalDateTime.now())){
            urlRepository.delete(entity);
            throw new EntityNotFoundException("Link expired!");
        }

        return entity.getLongUrl();
    }
}
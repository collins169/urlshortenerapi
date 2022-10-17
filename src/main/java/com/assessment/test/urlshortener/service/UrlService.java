package com.assessment.test.urlshortener.service;

import com.assessment.test.urlshortener.dto.UrlRequest;
import com.assessment.test.urlshortener.entity.Click;
import com.assessment.test.urlshortener.entity.Url;
import com.assessment.test.urlshortener.repository.ClickRepository;
import com.assessment.test.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private BaseEncryptor encryptor;
    @Autowired
    private ClickRepository clickRepository;

    public String convertToShortUrl(UrlRequest request) {
        Optional<Url> optionalUrl = urlRepository.findByLongUrlAndExpiresDateIsLessThan(request.getLongUrl(), LocalDateTime.now());
        if(optionalUrl.isPresent()){
            return encryptor.encode(optionalUrl.get().getId());
        }
        Url url = new Url();
        url.setLongUrl(request.getLongUrl());
        url.setExpiresDate(request.getExpiresDate());
        url.setCreatedDate(LocalDateTime.now());
        Url entity = urlRepository.save(url);

        return encryptor.encode(entity.getId());
    }

    public String getOriginalUrl(String shortUrl, String ip) {
        Long id = encryptor.decode(shortUrl);
        Url entity = urlRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no entity with " + shortUrl));

        if ( Objects.nonNull(entity.getExpiresDate()) && entity.getExpiresDate().isBefore(LocalDateTime.now())){
            urlRepository.delete(entity);
            throw new EntityNotFoundException("Link expired!");
        }

        if(!clickRepository.existsByUrl_LongUrlAndIpAddress(entity.getLongUrl(), ip)){
            Click click = new Click();
            click.setUrl(entity);
            click.setIpAddress(ip);
            clickRepository.save(click);
        }

        return entity.getLongUrl();
    }

    public boolean isValidUrl(String url){
        try {
            // it will check only for scheme and not null input
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    public String getBaseUrl(HttpServletRequest req) {
        return ""
                + req.getScheme() + "://"
                + req.getServerName()
                + ":" + req.getServerPort()
                + req.getContextPath();
    }
}
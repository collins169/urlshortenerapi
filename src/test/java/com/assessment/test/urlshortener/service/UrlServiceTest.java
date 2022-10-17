package com.assessment.test.urlshortener.service;

import com.assessment.test.urlshortener.dto.UrlRequest;
import com.assessment.test.urlshortener.entity.Url;
import com.assessment.test.urlshortener.repository.UrlRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UrlServiceTest {

    @Mock
    UrlRepository mockUrlRepository;

    @Mock
    BaseEncryptor mockBaseEncryptor;

    @InjectMocks
    UrlService urlService;

    @Test
    public void convertToShortUrlTest() {
        Url url = new Url();
        url.setLongUrl("https://github.com/collins169/urlshortenerapi");
        url.setCreatedDate(LocalDateTime.now());
        url.setId(5L);

        when(mockUrlRepository.save(any(Url.class))).thenReturn(url);
        when(mockBaseEncryptor.encode(url.getId())).thenReturn("f");

        UrlRequest urlRequest = new UrlRequest();
        urlRequest.setLongUrl("https://github.com/collins169/urlshortenerapi");

        assertEquals("f", urlService.convertToShortUrl(urlRequest));
    }

    @Test
    public void getOriginalUrlTest() {
        when(mockBaseEncryptor.decode("h")).thenReturn((long) 7);

        var url = new Url();
        url.setLongUrl("https://github.com/collins169/urlshortenerapi");
        url.setCreatedDate(LocalDateTime.now());
        url.setId(7L);

        when(mockUrlRepository.findById((long) 7)).thenReturn(java.util.Optional.of(url));
        assertEquals("https://github.com/collins169/urlshortenerapi", urlService.getOriginalUrl("h"));

    }
}

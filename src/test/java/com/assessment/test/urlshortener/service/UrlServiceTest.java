package com.assessment.test.urlshortener.service;

import com.assessment.test.urlshortener.dto.UrlRequest;
import com.assessment.test.urlshortener.entity.Click;
import com.assessment.test.urlshortener.entity.Url;
import com.assessment.test.urlshortener.repository.ClickRepository;
import com.assessment.test.urlshortener.repository.UrlRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UrlServiceTest {

    @Mock
    UrlRepository mockUrlRepository;

    @Mock
    BaseEncryptor mockBaseEncryptor;

    @Mock
    ClickRepository mockClickRepository;

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
        when(mockClickRepository.existsByUrl_LongUrlAndIpAddress("http://test.com", "127.0.0.1")).thenReturn(false);
        assertEquals("https://github.com/collins169/urlshortenerapi", urlService.getOriginalUrl("h", "127.0.0.1"));

    }

    @Test
    public void recordsUrlClickTest() {
        when(mockBaseEncryptor.decode("h")).thenReturn((long) 7);

        var url = new Url();
        url.setLongUrl("https://github.com/collins169/urlshortenerapi");
        url.setCreatedDate(LocalDateTime.now());
        url.setId(7L);

        when(mockUrlRepository.findById((long) 7)).thenReturn(java.util.Optional.of(url));
        when(mockClickRepository.existsByUrl_LongUrlAndIpAddress(url.getLongUrl(), "127.0.0.1")).thenReturn(false);
        assertEquals("https://github.com/collins169/urlshortenerapi", urlService.getOriginalUrl("h", "127.0.0.1"));
        Click click = new Click();
        click.setIpAddress("127.0.0.1");
        click.setUrl(url);
        click.setId(1L);
        when(mockClickRepository.save(any(Click.class))).thenReturn(click);
        verify(mockClickRepository, times(1)).save(any(Click.class));
    }
}

package com.assessment.test.urlshortener.controller;

import com.assessment.test.urlshortener.service.UrlService;
import com.assessment.test.urlshortener.dto.UrlRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class UrlController {
    @Autowired
    private UrlService urlService;

    @ApiOperation(value = "Convert new url", notes = "Converts long url to short url")
    @PostMapping("create")
    public ResponseEntity<String> convertToShortUrl(@RequestBody UrlRequest request, HttpServletRequest httpServletRequest) {
        if(!urlService.isValidUrl(request.getLongUrl())) return new ResponseEntity<>("Invalid url - "+request.getLongUrl(), HttpStatus.BAD_REQUEST);
        String shortenedUrl = urlService.convertToShortUrl(request);
        if(Objects.isNull(shortenedUrl)){
            return new ResponseEntity<>("Failed to shorten url try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(urlService.getBaseUrl(httpServletRequest)+"/api/v1/"+shortenedUrl, HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Redirect", notes = "Finds original url from short url and redirects")
    @GetMapping(value = "{shortUrl}")
    @Cacheable(value = "urls", key = "#shortUrl", sync = true)
    public ResponseEntity<Void> getAndRedirect(@PathVariable String shortUrl, HttpServletRequest httpServletRequest) {
        try {
            String url = urlService.getOriginalUrl(shortUrl, httpServletRequest.getRemoteAddr());
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(url))
                    .build();
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

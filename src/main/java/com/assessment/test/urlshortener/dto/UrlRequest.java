package com.assessment.test.urlshortener.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@ApiModel(description = "Request object for POST method")
@Data
public class UrlRequest {
    @ApiModelProperty(required = true, notes = "Url to convert to short")
    @NotEmpty(message = "Long url is required")
    private String longUrl;

    @ApiModelProperty(notes = "Expiration datetime of url")
    private LocalDateTime expiresDate;
}

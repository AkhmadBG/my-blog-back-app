package ru.yandex.practicum.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@Configuration
public class MultipartConfiguration {

    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

}
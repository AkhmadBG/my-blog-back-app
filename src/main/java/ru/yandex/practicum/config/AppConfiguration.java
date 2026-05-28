package ru.yandex.practicum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.mapper.CommentMapper;
import ru.yandex.practicum.mapper.PostMapper;

@Configuration
@ComponentScan("ru.yandex.practicum")
public class AppConfiguration {

//    @Bean
//    public PostMapper getPostMapperBean() {
//        return new PostMapper();
//    }

//    @Bean
//    public CommentMapper getCommentMapperBean() {
//        return new CommentMapper();
//    }

}
package ru.yandex.practicum.configuration;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.mapper.PostMapper;
import ru.yandex.practicum.repository.PostRepository;
import ru.yandex.practicum.service.PostService;
import ru.yandex.practicum.service.impl.PostServiceImpl;

@Configuration
public class PostServiceTestConfig {

    @Bean
    public PostRepository postRepository() {
        return Mockito.mock(PostRepository.class);
    }

    @Bean
    public PostMapper postMapper() {
        return Mockito.mock(PostMapper.class);
    }

    @Bean
    public PostService postService(PostRepository repository,
                                   @Qualifier("postMapper") PostMapper mapper) {
        return new PostServiceImpl(repository, mapper);
    }
    
}
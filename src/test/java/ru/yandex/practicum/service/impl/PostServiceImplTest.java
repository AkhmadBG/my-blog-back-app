package ru.yandex.practicum.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.configuration.ServiceTestConfiguration;
import ru.yandex.practicum.repository.PostRepository;
import ru.yandex.practicum.service.PostService;

@SpringBootTest
@Import(ServiceTestConfiguration.class)
class PostServiceImplTest {

    @Autowired
    private PostService postService;

    @Qualifier("mockPostRepository")
    @Autowired
    private PostRepository postRepository;

    @Test
    void getPosts() {
    }

    @Test
    void getPost() {
    }

    @Test
    void addPost() {
    }

    @Test
    void updatePost() {
    }

    @Test
    void deletePost() {
    }

    @Test
    void addLike() {
    }

    @Test
    void incrementOrDecrementPostCommentsCount() {
    }

    @Test
    void getImage() {
    }

    @Test
    void uploadImage() {
    }
}
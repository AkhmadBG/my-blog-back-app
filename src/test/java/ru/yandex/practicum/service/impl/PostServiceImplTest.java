package ru.yandex.practicum.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.mapper.PostMapper;
import ru.yandex.practicum.repository.PostRepository;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostServiceImpl postService;



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
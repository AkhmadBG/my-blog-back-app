package ru.yandex.practicum.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.configuration.ServiceTestConfiguration;
import ru.yandex.practicum.repository.CommentRepository;
import ru.yandex.practicum.service.CommentService;

@SpringBootTest
@Import(ServiceTestConfiguration.class)
class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @Qualifier("mockCommentRepository")
    @Autowired
    private CommentRepository commentRepository;

    @Test
    void getComments() {
    }

    @Test
    void getComment() {
    }

    @Test
    void addComment() {
    }

    @Test
    void updateComment() {
    }

    @Test
    void deleteComment() {
    }

}
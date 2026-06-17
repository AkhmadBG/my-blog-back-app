package ru.yandex.practicum.repository.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.configuration.DataSourceConfiguration;

@SpringBootTest
@Import(DataSourceConfiguration.class)
class CommentRepositoryImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void findAllByPostId() {
    }

    @Test
    void findByPostIdAndCommentId() {
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
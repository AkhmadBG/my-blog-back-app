package ru.yandex.practicum.repository.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.configuration.DataSourceConfiguration;

@SpringBootTest
@Import(DataSourceConfiguration.class)
class PostRepositoryImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void getPosts() {
    }

    @Test
    void countPosts() {
    }

    @Test
    void incrementOrDecrementPostCommentsCount() {
    }

    @Test
    void findPostById() {
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
    void save() {
    }

}
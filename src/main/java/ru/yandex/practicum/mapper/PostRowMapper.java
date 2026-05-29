package ru.yandex.practicum.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.entity.Post;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PostRowMapper implements RowMapper<Post> {

    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Post.builder()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .text(rs.getString("text"))
                .imagePath(rs.getString("image_path"))
                .likesCount(rs.getLong("likes_count"))
                .commentsCount(rs.getLong("comments_count"))
                .build();
    }

}
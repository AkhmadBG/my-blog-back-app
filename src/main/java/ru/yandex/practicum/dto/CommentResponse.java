package ru.yandex.practicum.dto;

public record CommentResponse(
        Long id,
        String text,
        Long postId
) {
}
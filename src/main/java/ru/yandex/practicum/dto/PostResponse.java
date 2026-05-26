package ru.yandex.practicum.dto;

import java.util.Set;

public record PostResponse(
        Long id,
        String title,
        String text,
        Set<String> tags,
        long likesCount,
        long commentsCount
) {
}
package ru.yandex.practicum.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateCommentRequest(
        @NotNull
        Long id,
        @NotBlank
        String text,
        @NotNull
        Long postId
) {
}
package ru.yandex.practicum.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewCommentRequest(
        @NotBlank
        String text,
        @NotNull
        Long postId
) {
}
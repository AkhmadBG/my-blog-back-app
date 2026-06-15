package ru.yandex.practicum.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record UpdatePostRequest(
        @NotNull
        Long id,
        @NotBlank
        String title,
        @NotBlank
        String text,
        @NotNull
        Set<String> tags
) {
}
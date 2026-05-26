package ru.yandex.practicum.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private Long id;

    @NotBlank
    private String text;

    @NotNull
    private Long postId;

}
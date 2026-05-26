package ru.yandex.practicum.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @NotNull
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String text;

    private Set<String> tags = new HashSet<>();

    private long likesCount = 0;

    private long commentsCount = 0;

}
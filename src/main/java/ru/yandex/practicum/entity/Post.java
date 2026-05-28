package ru.yandex.practicum.entity;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    private Long id;

    private String title;

    private String text;

    @Builder.Default
    private Set<String> tags = new HashSet<>();

    private String imagePath;

    @Builder.Default
    private long likesCount = 0;

    @Builder.Default
    private long commentsCount = 0;

}
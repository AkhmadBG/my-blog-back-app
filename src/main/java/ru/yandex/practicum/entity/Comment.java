package ru.yandex.practicum.entity;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private Long id;

    private String text;

    private Long postId;

}
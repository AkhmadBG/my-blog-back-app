package ru.yandex.practicum.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomPage<T> {

    private List<T> posts;

    private boolean hasPrev;

    private boolean hasNext;

    private int lastPage;

}
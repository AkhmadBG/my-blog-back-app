package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import ru.yandex.practicum.dto.PostResponse;
import ru.yandex.practicum.entity.Post;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostResponse mapToPostResponse(Post post);

}
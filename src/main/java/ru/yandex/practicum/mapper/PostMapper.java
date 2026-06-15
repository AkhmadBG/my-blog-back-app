package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.yandex.practicum.dto.PostResponse;
import ru.yandex.practicum.entity.Post;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostResponse mapToPostResponse(Post post);

    @Mapping(target = "text", source = "text", qualifiedByName = "shortText")
    PostResponse mapToPostResponseForList(Post post);

    @Named("shortText")
    default String shortText(String text) {
        if (text.length() <= 128) {
            return text;
        }
        return text.substring(0, 128) + "...";
    }

}
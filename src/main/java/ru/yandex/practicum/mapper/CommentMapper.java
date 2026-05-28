package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import ru.yandex.practicum.dto.CommentResponse;
import ru.yandex.practicum.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentResponse mapToCommentResponse(Comment comment);

}
package ru.yandex.practicum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handlePostNotFoundException(final PostNotFoundException e) {
        return new ApiError(
                HttpStatus.NOT_FOUND,
                "Пост не найден",
                e.getMessage()
        );
    }

    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleCommentNotFoundException(final CommentNotFoundException e) {
        return new ApiError(
                HttpStatus.NOT_FOUND,
                "Коммент не найден",
                e.getMessage()
        );
    }

    @ExceptionHandler(ImageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleImageNotFoundException(final ImageNotFoundException e) {
        return new ApiError(
                HttpStatus.NOT_FOUND,
                "Изображение не найдено",
                e.getMessage()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleException(final Exception e) {
        return new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Произошла ошибка",
                e.getMessage()
        );
    }

}
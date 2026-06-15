package ru.yandex.practicum;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MyBlogBackApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("ru.yandex.practicum");
    }

}
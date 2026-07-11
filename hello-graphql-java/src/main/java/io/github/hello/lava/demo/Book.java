package io.github.hello.lava.demo;

import java.util.List;

public record Book(String id, String title, String author) {

    /**
     * 模拟内存数据库
     */
    public static final List<Book> DATA_BOOS = List.of(
            new Book("1", "The Great Gatsby", "F. Scott Fitzgerald"),
            new Book("2", "1984", "George Orwell")
    );

}
package io.github.hello.spring.model;

import java.util.List;

public record Book(String id, String title, List<Author> authors) {}
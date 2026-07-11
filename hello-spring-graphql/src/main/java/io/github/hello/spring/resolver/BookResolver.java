package io.github.hello.spring.resolver;

import graphql.GraphQLContext;
import io.github.hello.spring.model.Author;
import io.github.hello.spring.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.DataLoader;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 在 Spring GraphQL 中，原生的 Resolver 被抽象成了 @Controller。
 * 通过 @QueryMapping 注解，Spring 会自动将 GraphQL 的查询路由到对应的方法上。
 */
@Slf4j
@Controller
public class BookResolver {

    // 模拟内存数据库
    private static final List<Book> books = List.of(
            new Book("1", "The Great Gatsby", List.of(new Author("1", "F. Scott"), new Author("2", "Fitzgerald"))),
            new Book("2", "1984", List.of(new Author("3", "George Orwell")))
    );

    /**
     * // 对应 Schema 中的 type Query 下的 books
     */
    @QueryMapping
    public List<Book> books(GraphQLContext ctx) {
        return books;
    }

    /**
     * // 对应 Schema 中的 bookById(id: ID!)
     * // @Argument 注解用于接收前端传来的参数
     */
    @QueryMapping
    public Book bookById(@Argument String id) {
        return books.stream()
                .filter(book -> book.id().equals(id))
                .findFirst()
                .orElse(null);
    }

//    @SchemaMapping(typeName = "Book", field = "authors")
//    public CompletableFuture<Author> author(Book book, DataLoader<Long, Author> dataLoader) {
//        return dataLoader.load(book.getAuthorId());
//    }


    @BatchMapping(typeName = "Book", field = "authors", maxBatchSize = 50)
    public Map<Book, List<Author>> authors(List<Book> books, GraphQLContext ctx) {
        log.info("@BatchMapping: {}", books);

        return books.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        Book::authors,
                        (o1, o2) -> o1
                ));
    }
}
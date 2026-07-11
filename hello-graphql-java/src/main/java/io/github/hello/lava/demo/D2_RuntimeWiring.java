package io.github.hello.lava.demo;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.TypeRuntimeWiring;

import java.util.List;
import java.util.function.UnaryOperator;

public class D2_RuntimeWiring {

    public static void main(String[] args) {

        DataFetcher<List<Book>> booksDataFetcher = new DataFetcher<>() {
            @Override
            public List<Book> get(DataFetchingEnvironment environment) throws Exception {
                return Book.DATA_BOOS;
            }
        };

        UnaryOperator<TypeRuntimeWiring.Builder> books = new UnaryOperator<>() {
            @Override
            public TypeRuntimeWiring.Builder apply(TypeRuntimeWiring.Builder builder) {
                return builder.dataFetcher("books", booksDataFetcher);
            }
        };


        RuntimeWiring.Builder wiring = RuntimeWiring.newRuntimeWiring();
        wiring.type("Query", books);

    }
}

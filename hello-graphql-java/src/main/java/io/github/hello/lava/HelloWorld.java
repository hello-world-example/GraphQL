package io.github.hello.lava;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

public class HelloWorld {

    public static void main(String[] args) {
        String schema = """
                type Query{
                    hello: String
                }
                """;

        // Parse Schema
        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(schema);

        // Fetcher && Loader
        StaticDataFetcher dataFetcher = new StaticDataFetcher("world");


        // Bind
        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("hello", dataFetcher))
                .build();
        GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();

        // execution
        ExecutionResult executionResult = build.execute("{hello}");
        System.out.println(executionResult.getData().toString());
        // Prints: {hello=world}
    }
}
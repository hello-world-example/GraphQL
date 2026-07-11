package io.github.hello.lava.product;

import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

public class GraphQlSchemaBuilder {

    public static GraphQLSchema buildSchema(BffDataFetchers dataFetchers) {
        String schemaSpec = """
                    type Query { topProducts: [Product] }
                    type Product { id: ID!, name: String!, price: Int!, reviews: [Review] }
                    type Review { score: Int!, comment: String! }
                """;

        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaSpec);
        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                // .type("Query", builder -> builder.dataFetcher("topProducts", dataFetchers.getTopProductsFetcher()))
                .type("Product", builder -> builder.dataFetcher("reviews", dataFetchers.getProductReviewsFetcher()))
                .build();

        return new SchemaGenerator().makeExecutableSchema(typeRegistry, runtimeWiring);
    }
}
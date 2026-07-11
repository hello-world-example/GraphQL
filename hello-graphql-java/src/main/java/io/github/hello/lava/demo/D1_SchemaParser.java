package io.github.hello.lava.demo;

import graphql.language.TypeDefinition;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

public class D1_SchemaParser {

    public static void main(String[] args) {
        InputStream demoSchema = Objects.requireNonNull(D1_SchemaParser.class.getResourceAsStream("/graphql/demo.graphql"));
        //
        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(demoSchema);
        //
        Map<String, TypeDefinition> types = typeDefinitionRegistry.types();
        System.out.println(types);

    }

}

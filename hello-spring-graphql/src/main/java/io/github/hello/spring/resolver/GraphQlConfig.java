package io.github.hello.spring.resolver;

import graphql.schema.idl.SchemaPrinter;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.GraphQlSource;

@Configuration
public class GraphQlConfig {

    @Bean
    public ApplicationRunner schemaExporter(GraphQlSource graphQlSource) {

        return args -> {
            var schema = graphQlSource.schema();
            // 你可以使用 SchemaPrinter 将其打印到控制台或输出到 target 目录，供 CI/CD 收集
            String schemaString = new SchemaPrinter().print(schema);
            System.out.println(schemaString);
        };
    }
}
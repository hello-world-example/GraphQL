package io.github.hello.spring.template;

import io.github.hello.spring.GraphQlApplication;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.ExecutionGraphQlService;
import org.springframework.graphql.support.DefaultExecutionGraphQlRequest;

import java.util.Map;

@SpringBootTest(classes = GraphQlApplication.class)
class GraphQlExecutionTemplateTest {

    @Resource
    private ExecutionGraphQlService graphQlExecution;

    @Test
    void execute() {
        DefaultExecutionGraphQlRequest graphQlRequest = new DefaultExecutionGraphQlRequest("{books{id}}", null, Map.of(), null, "123", null);

        graphQlExecution.execute(graphQlRequest)
                .subscribe(qlResponse -> {
                    System.out.println((Object) qlResponse.getData());
                });
    }

}

package io.github.hello.spring.resolver;

import graphql.execution.ExecutionId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
public class GraphQlInterceptor implements WebGraphQlInterceptor {

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {

        log.info("request.getDocument: {}", request.getDocument());

        // 从上游继承
        String traceId = request.getHeaders().getFirst("X-Trace-Id");
        // 上游灭有自己生成
        final String executionId = StringUtils.hasText(traceId) ? traceId : UUID.randomUUID().toString();

        request.configureExecutionInput((executionInput, builder) -> {
            builder
                    // 设置 executionId
                    .executionId(ExecutionId.from(executionId))
                    // 这里的 contextBuilder 就是 GraphQLContext 的构建器
                    .graphQLContext(context -> context.put("key1", "value1"));

            return builder.build();
        });

        return chain.next(request);
    }

}

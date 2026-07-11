package io.github.hello.lava.product;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.SchemaGenerator;
import io.github.hello.lava.product.loader.ReviewBatchLoader;
import org.dataloader.DataLoaderFactory;
import org.dataloader.DataLoaderRegistry;

public class GraphQlMain {

    private final GraphQL graphQL;
    private final ReviewBatchLoader reviewBatchLoader;

    public GraphQlMain(GraphQLSchema schema, ReviewBatchLoader reviewBatchLoader) {
        this.graphQL = GraphQL.newGraphQL(schema).build();
        this.reviewBatchLoader = reviewBatchLoader;
    }

    public ExecutionResult executeRequest(String query) {
        // 1. 针对当前 HTTP 请求，创建一个隔离的 DataLoader 注册表
        DataLoaderRegistry registry = new DataLoaderRegistry();

        // 2. 注册我们的评价加载器
        registry.register("reviewsLoader", DataLoaderFactory.newDataLoader(reviewBatchLoader.getLoader()));

        // 3. 将 registry 织入到执行输入中
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .dataLoaderRegistry(registry) // 必须绑定
                .build();

        // 4. 执行异步聚合
        // 引擎在解析完 topProducts 后，发现有 reviews 字段调用了 load()
        // 引擎会自动调用 registry.dispatch()，触发批量网络请求，最终并发拼装数据
        return graphQL.execute(executionInput);
    }

    public static void main(String[] args) {
        SchemaGenerator schemaGenerator = new SchemaGenerator();
    }
}
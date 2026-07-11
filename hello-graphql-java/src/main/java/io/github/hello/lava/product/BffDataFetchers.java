package io.github.hello.lava.product;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import io.github.hello.lava.product.vo.Product;
import io.github.hello.lava.product.vo.Review;
import org.dataloader.DataLoader;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BffDataFetchers {

    // private final ProductServiceClient productService;

    // public BffDataFetchers(ProductServiceClient productService) {
    // this.productService = productService;
    // }

    // // 1. 顶级查询：获取商品列表
    // public DataFetcher<CompletableFuture<List<Product>>> getTopProductsFetcher()
    // {
    // return environment ->
    // CompletableFuture.supplyAsync(productService::getTopProducts);
    // }

    // 2. 嵌套查询：为商品获取评价（核心并发点）
    public DataFetcher<CompletableFuture<List<Review>>> getProductReviewsFetcher() {
        return new DataFetcher<CompletableFuture<List<Review>>>() {
            @Override
            public CompletableFuture<List<Review>> get(DataFetchingEnvironment environment) throws Exception {
                // 获取当前正在解析的商品对象
                Product product = environment.getSource();
                String productId = product.id();

                // 从上下文中拿到 DataLoaderRegistry，再找到对应的 DataLoader
                DataLoader<String, List<Review>> dataLoader = environment.getDataLoader("reviewsLoader");

                // 关键：调用 load() 并没有真正发起网络请求，只是把 ID 注册到了队列里
                // 它会立刻返回一个未完成的 Future
                return dataLoader.load(productId);
            }
        };
    }
}
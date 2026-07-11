
package io.github.hello.lava.product.loader;

import io.github.hello.lava.product.vo.Review;
import org.dataloader.BatchLoader;
import org.dataloader.MappedBatchLoader;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ReviewBatchLoader {

    // 定义批量获取逻辑
    public BatchLoader<String, List<Review>> getLoader() {
        return new BatchLoader<String, List<Review>>() {
            @Override
            public CompletionStage<List<List<Review>>> load(List<String> productIds) {
                return CompletableFuture.supplyAsync(() -> {
                    // 调用后端批量接口，例如：SELECT * FROM reviews WHERE product_id IN (productIds)
                    // 注意：内部必须保证返回的 List<List<Review>> 顺序与输入的 productIds 一一对应
                    return List.of();
                });
            }
        };
    }

    public static void main(String[] args) {
        new MappedBatchLoader<String, List<Review>>() {
            @Override
            public CompletionStage<Map<String, List<Review>>> load(Set<String> productIds) {
                // 调用后端批量接口，例如：SELECT * FROM reviews WHERE product_id IN (productIds).
                // 一个 productId 对应多个 Review，无需保证与参数的一致性
                return null;
            }
        };
    }
}
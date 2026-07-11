package io.github.hello.lava.fetchwapper;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * 超时异常熔断
 */
public class TimeoutDataFetcherWrapper<T> implements DataFetcher<CompletableFuture<T>> {
    private final DataFetcher<T> delegate;
    private final long timeout;
    private final TimeUnit timeUnit;
    private final Executor executor;

    public TimeoutDataFetcherWrapper(DataFetcher<T> delegate, long timeout, TimeUnit timeUnit, Executor executor) {
        this.delegate = delegate;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.executor = executor;
    }

    @Override
    public CompletableFuture<T> get(DataFetchingEnvironment env) throws Exception {
        return CompletableFuture.supplyAsync(() -> {
                    try {
                        return delegate.get(env);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }, executor)
                .orTimeout(timeout, timeUnit) // Java 9+ 超时控制
                .exceptionally(throwable -> {
                    // 超时或异常后的降级处理，这里选择返回 null（或者返回空列表等默认值）
                    System.err.println("字段 " + env.getExecutionStepInfo().getPath() + " 执行超时或失败: " + throwable.getMessage());
                    return null;
                });
    }
}
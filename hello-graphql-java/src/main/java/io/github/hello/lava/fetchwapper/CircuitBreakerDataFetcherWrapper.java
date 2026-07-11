package io.github.hello.lava.fetchwapper;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;

import java.time.Duration;

public class CircuitBreakerDataFetcherWrapper<T> implements DataFetcher<T> {
    private final DataFetcher<T> delegate;
    private final CircuitBreaker circuitBreaker;

    public CircuitBreakerDataFetcherWrapper(DataFetcher<T> delegate, String name) {
        this.delegate = delegate;
        
        // 配置熔断器：失败率达 50% 熔断，熔断后等待 5 秒尝试恢复
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(5))
                .build();
        this.circuitBreaker = CircuitBreaker.of(name, config);
    }

    @Override
    public T get(DataFetchingEnvironment env) throws Exception {
        // 使用熔断器包裹真正的业务逻辑
        try {
            return circuitBreaker.executeCheckedSupplier(() -> {
                try {
                    return delegate.get(env);
                } catch (Exception e) {
                    throw new Exception(e);
                }
            });
            // 可以在此处紧跟 .recover() 方法实现熔断后的 Fallback 降级逻辑
        } catch (Throwable e) {
            throw new Exception(e);
        }
    }
}
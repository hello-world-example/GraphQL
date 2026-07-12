package io.github.hello.dataloader;

import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class Main {
    public static void main(String[] args) {
        DataLoader<Object, Object> dataLoader = DataLoaderFactory.newDataLoader(new BatchLoader<Object, Object>() {
            @Override
            public CompletionStage<List<Object>> load(List<Object> keys) {
                return null;
            }
        });

        CompletableFuture<List<Object>> dispatch = dataLoader.dispatch();



    }
}
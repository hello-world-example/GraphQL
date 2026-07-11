package io.github.hello.spring.tmp;

import com.fasterxml.jackson.annotation.JsonProperty;

public record User(
        Long id,
        @JsonProperty("realName")
        String name
) {
    /**
     * 紧凑构造函数，隐式包含所有参数
     */
    public User {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("ID不合法");
        }
    }

    /**
     * 一般构造函数
     */
    public User(String name) {
        this(0L, name);
    }
}
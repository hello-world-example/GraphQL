package io.github.hello.spring.tmp;


import tools.jackson.databind.ObjectMapper;

public class JacksonRecordDemo {
    public static void main(String[] args) {
        User user = new User(101L, "张三");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResult = objectMapper.writeValueAsString(user);
        System.out.println(jsonResult);
        // 输出: {"id":101,"realName":"张三"}
    }
}
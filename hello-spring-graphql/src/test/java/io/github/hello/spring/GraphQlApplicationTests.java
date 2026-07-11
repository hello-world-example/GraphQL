package io.github.hello.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = GraphQlApplication.class)
class GraphQlApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("ok");
    }

}

package cn.com.cgh.ayth;

import reactor.core.publisher.Mono;

public class TestMain {
    public static void main(String[] args) {
        Mono<String> mono = Mono.just("Hello, World!")
                .doOnSuccess(value -> System.out.println("Successfully emitted: " + value));

        mono.subscribe();
    }
}

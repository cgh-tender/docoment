package cn.com.cgh.resource;

import reactor.core.publisher.Mono;

public class JavaTest {
    public static void main(String[] args) {
        Mono.empty().map(a -> {
                    System.out.println(a);
                    return a;
                }
        ).switchIfEmpty(Mono.just("123")).map(a->{
            System.out.println(a);
            return a;
        }).subscribe();
    }
}

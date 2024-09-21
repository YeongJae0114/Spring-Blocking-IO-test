package org.example.server2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class BookController {
    @GetMapping("/books/{id}/title")
    public Mono<String> getBookTitle(@PathVariable String id) throws InterruptedException {
        Thread.sleep(5000);
        return Mono.just("Title " + id);
    }

    @GetMapping("/books/{id}/content")
    public Mono<String> getBookContent(@PathVariable String id) throws InterruptedException {
        Thread.sleep(5000);
        return Mono.just("Content " + id);
    }
}

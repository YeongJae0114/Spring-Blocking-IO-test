package org.example.server1.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Slf4j
@RestController
public class BookController {
    private final RestTemplate restTemplate = new RestTemplate();
    private final WebClient webClient = WebClient.create();

    @GetMapping("block/books/{id}")
    public BookReadResponse getBookV1(@PathVariable String id){
        // server2의 요청 url
        String url = "http://localhost:8081/books/" + id + "/title";


        // 주어진 URL 주소로 HTTP GET 메서드로 객체로 결과를 반환받는다
        String title = restTemplate.getForObject(url, String.class);

        url = "http://localhost:8081/books/" + id + "/content";
        String content = restTemplate.getForObject(url, String.class);

        return new BookReadResponse(title, content);
    }

    // Mono는 Reactor 라이브러리에서 제공하는 리액티브 스트림 API 중 하나로,
    // 단일 요소 또는 없음을 비동기적으로 처리하는 컨테이너
    @GetMapping("nonblock/books/{id}")
    public Mono<BookReadResponse> getBookV2(@PathVariable String id) {
        String url = "http://localhost:8081/books/" + id + "/title";

        Mono<String> title = webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);

        url = "http://localhost:8081/books/" + id + "/content";

        Mono<String> content = webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);
        return Mono.zip(title, content)
                .map(tuple -> new BookReadResponse(tuple.getT1(), tuple.getT2()));
    }
}

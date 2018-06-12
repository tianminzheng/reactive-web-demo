package com.tianyalan.reactive.demo.server.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tianyalan.reactive.demo.server.model.Event;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Stream;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
public class DemoServerController {

    @GetMapping("/event/{id}")
    public Mono<Event> getOneInfoById(@PathVariable("id") String id) {
        return Mono.just(new Event(id, LocalDateTime.now()));
    }


    @GetMapping(value = "/events", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Event> getInfosAsStream() {

        Flux<Event> dtoFlux = Flux.fromStream(Stream.generate(() -> new Event(UUID.randomUUID().toString(), LocalDateTime.now())));
        Flux<Long> duration = Flux.interval(Duration.ofMillis(100));

        return Flux.zip(dtoFlux, duration).map(Tuple2::getT1);
    }

}

package com.tianyalan.reactive.demo.client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.tianyalan.reactive.demo.client.model.Event;
import com.tianyalan.reactive.demo.client.model.EventId;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

import reactor.core.publisher.Flux;

@RestController
public class DemoClientController {

    private WebClient webClient;

    @Autowired
    public DemoClientController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Set<EventId>> getEventsStream() {
        return webClient
                .get()
                .uri("/events")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToFlux(Event.class)
                .buffer(Duration.ofSeconds(3))
                .flatMap(events -> {
                    Set<EventId> eventIds = events.stream().map(event -> new EventId(event.getId())).collect(Collectors.toSet());
                    return Flux.just(eventIds);
                });
    }

}

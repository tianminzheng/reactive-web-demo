package com.tianyalan.reactive.demo.server.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Event {

    private final String id;
    private final LocalDateTime time;

}

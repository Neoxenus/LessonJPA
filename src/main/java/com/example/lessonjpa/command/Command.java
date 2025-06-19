package com.example.lessonjpa.command;

import org.springframework.stereotype.Component;

@Component
public interface Command {
    void execute();
    int getCode();
    String getDescription();
}

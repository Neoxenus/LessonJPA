package com.example.lessonjpa.command.implementations.util;

import com.example.lessonjpa.command.BaseCommand;
import org.springframework.stereotype.Component;

@Component
public class Exiting extends BaseCommand {
    public Exiting() {
        super(0, "Type " + 0 + " to exit");
    }

    @Override
    public void execute() {
        System.out.println("Exiting application...");
        System.exit(0);
    }
}

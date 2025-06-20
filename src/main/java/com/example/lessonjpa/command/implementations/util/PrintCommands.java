package com.example.lessonjpa.command.implementations.util;

import com.example.lessonjpa.command.BaseCommand;
import com.example.lessonjpa.command.Command;
import com.example.lessonjpa.command.CommandContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class PrintCommands extends BaseCommand {

    private final CommandContainer commandContainer;

    @Autowired
    public PrintCommands(@Lazy CommandContainer commandContainer) {
        super(-1, "Type -1 to print all commands");
        this.commandContainer = commandContainer;
    }

    @Override
    public void execute() {
        commandContainer.getAllCommands().stream()
                .sorted(Comparator.comparingInt(Command::getCode))
                .forEach(cmd -> System.out.println(cmd.getDescription()));
    }
}

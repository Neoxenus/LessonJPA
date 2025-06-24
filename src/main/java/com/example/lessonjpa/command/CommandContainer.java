package com.example.lessonjpa.command;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CommandContainer {
    private final Map<Integer, Command> commands;

    public CommandContainer(List<Command> commandList) {
        commands = commandList.stream()
                .collect(Collectors.toMap(Command::getCode, Function.identity()));
    }


    public Command getCommand(Integer command) {
        return commands.get(command);
    }

    public void doCommand(Command command) {
        if(command == null){
            System.out.println("Incorrect command");
            return;
        }
        command.execute();
    }

    public Collection<Command> getAllCommands(){
        return commands.values();
    }
}

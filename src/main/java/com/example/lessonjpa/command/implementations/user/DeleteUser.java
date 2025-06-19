package com.example.lessonjpa.command.implementations.user;

import com.example.lessonjpa.command.BaseCommand;
import com.example.lessonjpa.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class DeleteUser extends BaseCommand {

    private final UserService userService;

    @Autowired
    public DeleteUser(UserService userService) {
        super(4, "Type 4 to delete user");
        this.userService = userService;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter id of user that you want to delete");
        int id;
        try{
            id = Integer.parseInt(scanner.nextLine());
        } catch (Exception e){
            System.out.println("Error:\nId must be a number");
            return;
        }

        System.out.println(userService.deleteUser(id));
    }
}

package com.example.lessonjpa.command.implementations.user;

import com.example.lessonjpa.command.BaseCommand;
import com.example.lessonjpa.entities.DTO.UserDTO;
import com.example.lessonjpa.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateUser extends BaseCommand {
    private final UserService userService;

    @Autowired
    public CreateUser(UserService userService) {
        super(2, "Type 2 to save new user");
        this.userService = userService;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter user name: ");
        String name = scanner.nextLine();

        System.out.println("Enter user surname: ");
        String surname = scanner.nextLine();

        System.out.println("Enter user age: ");
        int age;
        try{
            age = Integer.parseInt(scanner.nextLine());
        } catch (Exception e){
            System.out.println("Error:\nAge must be number");
            return;
        }
        System.out.println("Enter user email: ");


        String email = scanner.nextLine();
        UserDTO user = new UserDTO(name, surname, age, email);

        System.out.println(userService.addUser(user));
    }
}

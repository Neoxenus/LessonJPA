package com.example.lessonjpa.command.implementations.credit;

import com.example.lessonjpa.command.BaseCommand;
import com.example.lessonjpa.services.CreditService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class PrintAllCreditsOfUser extends BaseCommand {
    private final CreditService creditService;

    public PrintAllCreditsOfUser(CreditService creditService) {
        super(14, "Type 14 to show all credits of user");
        this.creditService = creditService;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter user id: ");
        int userId;
        try{
            userId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e){
            System.out.println("Error:\nUser id must be a number");
            return;
        }
        System.out.println(creditService.showAllByUserId(userId));
    }
}

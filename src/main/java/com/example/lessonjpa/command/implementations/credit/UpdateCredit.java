package com.example.lessonjpa.command.implementations.credit;

import com.example.lessonjpa.command.BaseCommand;
import com.example.lessonjpa.entities.DTO.CreditDTO;
import com.example.lessonjpa.entities.enums.RateType;
import com.example.lessonjpa.services.CreditService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class UpdateCredit extends BaseCommand {
    private final CreditService creditService;
    public UpdateCredit(CreditService creditService) {
        super(12, "Type 12 to update credit");
        this.creditService = creditService;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter credit id: ");
        int creditId;
        try{
            creditId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e){
            System.out.println("Error:\nCredit id must be a number");
            return;
        }

        System.out.println("Enter new amount for crediting:");
        double amount;
        try{
            amount = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e){
            System.out.println("Error:\nAmount must be a number");
            return;
        }

        System.out.println("Enter new credit card id: ");
        int creditCardId;
        try{
            creditCardId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e){
            System.out.println("Error:\nCredit card id must be a number");
            return;
        }

        System.out.println("Enter new duration of credit: ");
        int duration;
        try{
            duration = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e){
            System.out.println("Error:\nDuration must be a number");
            return;
        }

        System.out.println("Enter new interest rate for crediting (in percentage):");
        double interestRate;
        try{
            interestRate = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e){
            System.out.println("Error:\nInterest rate must be a number");
            return;
        }

        System.out.println("Enter type of crediting ([M/m] for monthly and [Y/y] for yearly payments):");
        RateType rateType =
                switch (scanner.nextLine().toLowerCase()) {
                    case "y" -> RateType.YEARLY;
                    case "m" -> RateType.MONTHLY;
                    default -> null;
                };
        if(rateType == null){
            System.out.println("Error:\nIncorrect input enter y or Y for yearly payments and m or M for monthly");
            return;
        }

        System.out.println(creditService.updateCredit(creditId,
                new CreditDTO(amount, creditCardId, duration, interestRate, rateType)));
    }
}

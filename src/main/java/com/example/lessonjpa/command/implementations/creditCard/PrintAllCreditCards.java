package com.example.lessonjpa.command.implementations.creditCard;

import com.example.lessonjpa.command.BaseCommand;
import com.example.lessonjpa.services.CreditCardService;
import org.springframework.stereotype.Component;

@Component
public class PrintAllCreditCards extends BaseCommand {

    private final CreditCardService creditCardService;

    public PrintAllCreditCards(CreditCardService creditCardService) {
        super(6, "Type 6 to show all credit cards");
        this.creditCardService = creditCardService;
    }
    @Override
    public void execute() {
        System.out.println(creditCardService.showAll());
    }
}

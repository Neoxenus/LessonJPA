package com.example.lessonjpa.command.implementations.credit;

import com.example.lessonjpa.command.BaseCommand;
import com.example.lessonjpa.services.CreditService;
import org.springframework.stereotype.Component;

@Component
public class PrintAllCredits extends BaseCommand {

    private final CreditService creditService;

    public PrintAllCredits(CreditService creditService) {
        super(10, "Type 10 to show all credits");
        this.creditService = creditService;
    }

    @Override
    public void execute() {
        System.out.println(creditService.showAll());
    }
}

package com.example.lessonjpa.services.implementations;

import com.example.lessonjpa.entities.Credit;
import com.example.lessonjpa.entities.CreditCard;
import com.example.lessonjpa.entities.DTO.CreditDTO;
import com.example.lessonjpa.repositories.CreditCardRepository;
import com.example.lessonjpa.repositories.CreditRepository;
import com.example.lessonjpa.services.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CreditServiceImpl implements CreditService {
    private final CreditRepository creditRepository;
    private final CreditCardRepository creditCardRepository;

    private final SmartValidator validator;

    @Autowired
    public CreditServiceImpl(CreditRepository creditDAO, CreditCardRepository creditCardDAO, SmartValidator validator) {
        this.creditRepository = creditDAO;
        this.creditCardRepository = creditCardDAO;
        this.validator = validator;
    }

    @Override
    public String showAll(){
        List<Credit> credits = creditRepository.findAll();
        if (!credits.isEmpty())
            return credits.stream().map(Credit::toString).collect(Collectors.joining("\n"));
        else
            return "No credits to display";
    }


    @Override
    public String showAllByUserId(int userId){
        List<Credit> credits = creditRepository.findByCreditCardUserId(userId);
        if (!credits.isEmpty())
            return credits.stream().map(Credit::toString).collect(Collectors.joining("\n"));
        else
            return "No credits to display";
    }

    @Transactional
    @Override
    public String addCredit(CreditDTO creditDTO){
        String errors = "Error\n";
        boolean isErrors = false;

        CreditCard creditCard = null;

        if(!creditCardRepository.existsById(creditDTO.getCreditCardId())){
            errors += "No credit card with such an id";
            isErrors = true;
        }
        try {
            creditCard = creditCardRepository.findById(creditDTO.getCreditCardId())
                    .orElseThrow(() -> new NoSuchElementException("No credit card with such an id"));
        } catch (NoSuchElementException e){
            errors += e.getMessage();
            isErrors = true;
        }

        Credit credit = new Credit(creditDTO, creditCard);

        BindingResult bindingResult = new BeanPropertyBindingResult(credit, "credit");
        validator.validate(credit, bindingResult);

        if(bindingResult.hasErrors() || isErrors){
            return errors + bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("\n"));
        } else {
            try {
                creditRepository.save(credit);
            } catch (Exception e){
                errors += "Something went wrong:\n" + e.getMessage();
                return errors;
            }

            return "Added";
        }
    }

    @Transactional
    @Override
    public String updateCredit(int id, CreditDTO updatedCredit) {
        String errors = "Error\n";


        CreditCard creditCard;
        try {
            creditCard = creditCardRepository.findById(updatedCredit.getCreditCardId()).orElseThrow();
        } catch (NoSuchElementException e){
            errors += "No credit card with such an id";
            return errors;
        }
        Optional<Credit> optionalCredit = creditRepository.findById(id);
        if(optionalCredit.isEmpty()){
            errors += "No credit with such an id";
            return errors;
        }

        Credit credit = optionalCredit.get().setValues(updatedCredit, creditCard);

        BindingResult bindingResult = new BeanPropertyBindingResult(credit, "credit");
        validator.validate(credit, bindingResult);

        if(bindingResult.hasErrors()){
            return errors + bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("\n"));
        } else {
            try {
                creditRepository.save(credit);
            } catch (Exception e){
                errors += "Something went wrong:\n" + e.getMessage();
                return errors;
            }

            return "Updated";
        }
    }

    @Transactional
    @Override
    public String deleteCredit(int id){
        String errors = "Error\n";
        if (creditRepository.existsById(id)) {
            try {
                creditRepository.deleteById(id);
            } catch (Exception e){
                errors += "Something went wrong:\n" + e.getMessage();
                return errors;
            }

            return "Deleted";
        } else {
            errors += "No credits with such an id\n";
            return errors;
        }
    }

}

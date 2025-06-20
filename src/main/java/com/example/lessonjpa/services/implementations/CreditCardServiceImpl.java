package com.example.lessonjpa.services.implementations;

import com.example.lessonjpa.entities.CreditCard;
import com.example.lessonjpa.entities.DTO.CreditCardDTO;
import com.example.lessonjpa.entities.User;
import com.example.lessonjpa.repositories.CreditCardRepository;
import com.example.lessonjpa.repositories.UserRepository;
import com.example.lessonjpa.services.CreditCardService;
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
@Transactional
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepository creditCardRepository;

    private final UserRepository userRepository;

    private final SmartValidator validator;

    @Autowired
    public CreditCardServiceImpl(CreditCardRepository creditCardRepository, UserRepository userRepository, SmartValidator validator) {
        this.creditCardRepository = creditCardRepository;
        this.userRepository = userRepository;
        this.validator = validator;
    }

    @Transactional(readOnly = true)
    @Override
    public String showAll() {
        List<CreditCard> creditCards = creditCardRepository.findAll();
        if (!creditCards.isEmpty())
            return creditCards.stream().map(CreditCard::toString).collect(Collectors.joining("\n"));
        else
            return "Nothing to display";
    }

    @Override
    public String addCreditCard(CreditCardDTO creditCardDTO) {

        String errors = "Error\n";
        boolean existsWithNumber = creditCardRepository.existsByNumber(creditCardDTO.getNumber());
        if(existsWithNumber){
            errors += "Credit card with such an number already exists\n";
        }

        User user;
        try {
            user = userRepository.findById(creditCardDTO.getUserId()).orElseThrow();
        } catch (NoSuchElementException e){
            errors += "No user with such an id\n";
            return errors;
        }

        CreditCard creditCard = new CreditCard(creditCardDTO, user);

        BindingResult bindingResult = new BeanPropertyBindingResult(creditCard, "credit card");
        validator.validate(creditCard, bindingResult);

        if(bindingResult.hasErrors() || existsWithNumber){
            return errors + bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("\n"));
        } else {
            try {
                creditCardRepository.save(creditCard);
            } catch (Exception e){
                errors += "Something went wrong:\n" + e.getMessage();
                return errors;
            }
            return "Added";
        }
    }

    @Override
    public String updateCreditCard(int id, CreditCardDTO creditCardDTO) {

        String errors = "Error\n";
        boolean existsWithNumber =  creditCardRepository.findByNumber(creditCardDTO.getNumber())
                .map(card -> !card.getId().equals(id))
                .orElse(false);


        if(existsWithNumber)
            errors += "Credit card with such an number already exists\n";


        User user;
        try {
            user = userRepository.findById(creditCardDTO.getUserId()).orElseThrow();
        } catch (NoSuchElementException e){
            errors += "No user with such an id\n";
            return errors;
        }
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(id);
        if(optionalCreditCard.isEmpty()){
            errors += "No credit card with such an id\n";
            return errors;
        }

        CreditCard creditCard = optionalCreditCard.get().setValues(creditCardDTO, user);

        BindingResult bindingResult = new BeanPropertyBindingResult(creditCard, "credit card");
        validator.validate(creditCard, bindingResult);

        if(bindingResult.hasErrors() || existsWithNumber){
            return errors + bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("\n"));
        } else {
            try {
                creditCardRepository.save(creditCard);
            } catch (Exception e){
                errors += "Something went wrong:\n" + e.getMessage();
                return errors;
            }
            return "Updated";
        }
    }

    @Override
    public String deleteCreditCard(int id) {

        String errors = "Error\n";
        if (creditCardRepository.existsById(id)) {
            try {
                creditCardRepository.deleteById(id);
            } catch (Exception e){
                errors += "Something went wrong:\n" + e.getMessage();
                return errors;
            }
            return "Deleted";
        } else {
            errors += "No credit card with such id\n";
            return errors;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public String findByUserId(int userId) {

        String errors = "Error\n";
        if(!userRepository.existsById(userId)) {
            errors += "No users with such id\n";
            return errors;
        } else {
            List<CreditCard> creditCards = creditCardRepository.findByUserId(userId);
            if (!creditCards.isEmpty())
                return creditCards.stream().map(CreditCard::toString).collect(Collectors.joining("\n"));
            else
                return "Nothing to display";

        }
    }
}

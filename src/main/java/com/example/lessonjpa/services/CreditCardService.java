package com.example.lessonjpa.services;

import com.example.lessonjpa.entities.DTO.CreditCardDTO;
import org.springframework.stereotype.Service;

@Service
public interface CreditCardService {


    String showAll();

    String addCreditCard(CreditCardDTO creditCard);

    String updateCreditCard(int id, CreditCardDTO creditCard);

    String deleteCreditCard(int id);

    String findByUserId(int userId);
}

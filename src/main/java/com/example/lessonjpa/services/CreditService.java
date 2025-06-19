package com.example.lessonjpa.services;

import com.example.lessonjpa.entities.DTO.CreditDTO;
import org.springframework.stereotype.Service;

@Service
public interface CreditService {

    String showAll();

    String showAllByUserId(int userId);

    String addCredit(CreditDTO creditDTO);
    String updateCredit(int id, CreditDTO updatedCredit);

    String deleteCredit(int id);

}

package com.example.lessonjpa.repositories;

import com.example.lessonjpa.entities.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {
    List<CreditCard> findByUserId(Integer id);
}

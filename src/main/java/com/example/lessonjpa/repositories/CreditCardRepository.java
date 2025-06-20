package com.example.lessonjpa.repositories;

import com.example.lessonjpa.entities.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {
    List<CreditCard> findByUserId(Integer id);
    boolean existsByNumber(String email);

    Optional<CreditCard> findByNumber(String number);
}

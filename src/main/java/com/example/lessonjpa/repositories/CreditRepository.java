package com.example.lessonjpa.repositories;

import com.example.lessonjpa.entities.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Integer> {


    List<Credit> findByCreditCardUserId(int userId);

}

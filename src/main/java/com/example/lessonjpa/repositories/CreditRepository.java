package com.example.lessonjpa.repositories;

import com.example.lessonjpa.entities.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Integer> {


    @Query("SELECT c FROM Credit c WHERE c.creditCard.user.id = :userId")
    List<Credit> findByCreditCardByUserId(@Param("userId") int userId);

}

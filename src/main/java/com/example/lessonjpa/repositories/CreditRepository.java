package com.example.lessonjpa.repositories;

import com.example.lessonjpa.entities.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Integer> {


//    @Query("SELECT c FROM Credit c WHERE c.creditCard.user.id = :userId")
//    List<Credit> findAllCreditsByUserId(@Param("userId") int userId);

    //test and mb leave commented method via query
    List<Credit> findByCreditCardByUserId(Integer userId);
}

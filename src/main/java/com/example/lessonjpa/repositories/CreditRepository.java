package com.example.lessonjpa.repositories;

import com.example.lessonjpa.entities.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Integer> {


//    @Query("SELECT c FROM Credit c WHERE c.creditCard.user.id = :userId")
//    List<Credit> findByCreditCardUserId(@Param("userId") int userId);
    List<Credit> findByCreditCardUserId(int userId);

}

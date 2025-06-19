package com.example.lessonjpa.entities.DTO;


import com.example.lessonjpa.entities.enums.RateType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreditDTO {

    private Double amount;

    private Integer creditCardId;

    private Integer duration;

    private Double interestRate;

    private RateType rateType;

}

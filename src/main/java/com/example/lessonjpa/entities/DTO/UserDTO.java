package com.example.lessonjpa.entities.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDTO {

    private String name;

    private String surname;


    private Integer age;


    private String email;

}

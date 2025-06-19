package com.example.lessonjpa.services;

import com.example.lessonjpa.entities.DTO.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    String showAll();

    String addUser(UserDTO user);

    String updateUser(int id, UserDTO updatedUser);

    String deleteUser(int id);

}

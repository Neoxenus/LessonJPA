package com.example.lessonjpa.services.implementations;

import com.example.lessonjpa.entities.DTO.UserDTO;
import com.example.lessonjpa.entities.User;
import com.example.lessonjpa.repositories.UserRepository;
import com.example.lessonjpa.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SmartValidator validator;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, SmartValidator validator) {
        this.userRepository = userRepository;
        this.validator = validator;
    }

    @Transactional(readOnly = true)
    @Override
    public String showAll() {
        List<User> users = userRepository.findAll();
        if (!users.isEmpty())
            return users.stream().map(User::toString).collect(Collectors.joining("\n"));
        else
            return "Nothing to display";
    }

    @Transactional
    @Override
    public String addUser(UserDTO userDTO) {
        String errors = "Error\n";

        boolean existsWithEmail = userRepository.existsByEmail(userDTO.getEmail());

        if(existsWithEmail) {
            errors += "User with such an email already exists\n";
        }

        User user = new User(userDTO);

        BindingResult bindingResult = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, bindingResult);

        if(bindingResult.hasErrors()){
            return errors + bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("\n"));
        } else {
            try {
                userRepository.save(user);
            } catch (Exception e){
                errors += "Something went wrong:\n" + e.getMessage();
                return errors;
            }

            return "Added";
        }
    }

    @Override
    public String updateUser(int id, UserDTO userDTO) {
        String errors = "Error\n";

        boolean existsWithEmail = userRepository.findByEmail(userDTO.getEmail())
                .map(user -> !user.getId().equals(id))
                .orElse(false);

        if(existsWithEmail){
            errors += "User with such an email already exists\n";
        }

        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            errors += "No users with such id\n";
            return errors;
        }
        User user = optionalUser.get().setValues(userDTO);

        BindingResult bindingResult = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, bindingResult);

        if(bindingResult.hasErrors() || existsWithEmail){
            return errors + bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("\n"));
        } else {
            try {
                userRepository.save(user);
            } catch (Exception e){
                errors += "Something went wrong:\n" + e.getMessage();
                return errors;
            }

            return "Updated";
        }
    }

    @Override
    public String deleteUser(int id) {
        String errors = "Error\n";
        if(!userRepository.existsById(id)){
            errors += "No users with such id\n";
            return errors;
        } else {
            try {
                userRepository.deleteById(id);
            } catch (Exception e){
                errors += "Something went wrong:\n" + e.getMessage();
                return errors;
            }

            return "Deleted";
        }
    }
}

package com.baeldung.crud.controllers;

import javax.validation.Valid;

import com.baeldung.crud.service.PodInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.baeldung.crud.entities.User;
import com.baeldung.crud.repositories.UserRepository;

@Controller
@Slf4j
public class UserController {
    
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void updateModel( Model model ) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("podInfo", new PodInfo());
    }

    @GetMapping("/")
    public String startVisit(Model model) {
        log.info("Visiting site");
        updateModel(model);
        return "index";
    }
    
    @GetMapping("/signup")
    public String showSignUpForm(User user, Model model) {
        updateModel(model);
        return "add-user";
    }
    
    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            updateModel(model);
            return "add-user";
        }
        
        userRepository.save(user);
        updateModel(model);
        return "index";
    }
    
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") String id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        updateModel(model);
        return "update-user";
    }
    
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") String id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            updateModel(model);
            return "update-user";
        }
        
        userRepository.save(user);
        updateModel(model);
        return "index";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") String id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        updateModel(model);
        return "index";
    }
}

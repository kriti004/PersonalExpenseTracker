package com.personalExpenseTracker.personalExpenseTracker.controller;

import com.personalExpenseTracker.personalExpenseTracker.entity.User;
import com.personalExpenseTracker.personalExpenseTracker.service.UserService;
import com.personalExpenseTracker.personalExpenseTracker.user.UserDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showRegistrationForm")
    public String showMyLoginPage(Model theModel) {
        log.info("calling showRegistrationForm");
        theModel.addAttribute("webUser", new UserDTO());
        return "registration-form";
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(@Valid @ModelAttribute("webUser") UserDTO theUserDTO, 
                                          BindingResult theBindingResult, HttpSession session, Model model) throws Exception {

        if (theBindingResult.hasErrors()) {
            return "registration-form";
        }
        String userName = theUserDTO.getUserName();
        log.info("Processing registration form for: " + userName);
        try {
            User userExists = userService.findByUsername(userName);
            if (userExists != null) {
                log.warn("User name already exists.");
                model.addAttribute("webUser", new UserDTO());
                model.addAttribute("registrationError", "User name already exists.");
                return "registration-form";
            }
        } catch (Exception e) {
            log.warn("User not found so creating a new entry in db for username : " + userName);
            userService.save(theUserDTO);
            log.info("Successfully created user: " + userName);
        }
        session.setAttribute("user", theUserDTO);
        return "homePage";
    }
}

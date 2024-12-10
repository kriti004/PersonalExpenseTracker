package com.personalExpenseTracker.personalExpenseTracker.controller;

import com.personalExpenseTracker.personalExpenseTracker.entity.Expense;
import com.personalExpenseTracker.personalExpenseTracker.entity.User;
import com.personalExpenseTracker.personalExpenseTracker.repository.UserRepository;
import com.personalExpenseTracker.personalExpenseTracker.service.ExpenseService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    private final ExpenseService expenseService;
    private final UserRepository userRepository;

    public MainController(ExpenseService expenseService, UserRepository userRepository) {
        this.expenseService = expenseService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String openHomePage(Model model, Authentication authentication) {
        Optional<User> userOptional = userRepository.findByUsername(authentication.getName());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Expense> expenseList = expenseService.findExpensesByUserId(user.getId());
            model.addAttribute("message", "Welcome to your expense tracker!");  // Add a message or any other data
            model.addAttribute("expenses", expenseList);
            model.addAttribute("user", user);
            return "homePage";
        }
        return "/";
    }

    @GetMapping("/showMyLoginPage")
    public String openLoginPage(){
        return "loginPage";
    }

}
package com.personalExpenseTracker.personalExpenseTracker.controller;

import com.personalExpenseTracker.personalExpenseTracker.entity.User;
import com.personalExpenseTracker.personalExpenseTracker.service.CsvExportService;
import com.personalExpenseTracker.personalExpenseTracker.service.ExpenseService;
import com.personalExpenseTracker.personalExpenseTracker.service.UserService;
import com.personalExpenseTracker.personalExpenseTracker.user.ExpenseDTO;
import com.personalExpenseTracker.personalExpenseTracker.user.Expense;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    @Autowired
    private CsvExportService csvExportService;


    @GetMapping("/expenses")
    public String ExpenseByUserId(@RequestParam("userId") int userId, Model model) throws Exception {
        User user = userService.findUserById(userId);
        List<com.personalExpenseTracker.personalExpenseTracker.entity.Expense> expenseList = expenseService.findExpensesByUser(user);
        model.addAttribute("expenses", expenseList);
        return "homePage";
    }

    @GetMapping("/expenses/category")
    public String ExpenseByCategory(@RequestParam String category, Model model) {
        List<com.personalExpenseTracker.personalExpenseTracker.entity.Expense> expenseList = expenseService.findExpensesByCategory(category);
        model.addAttribute("expenses", expenseList);
        return "homePage";
    }

    @GetMapping("/showExpenseForm")
    public String showExpenseForm(Model model) {
        model.addAttribute("expenseDTO", new ExpenseDTO());
        return "AddForm";
    }

    @PostMapping("/addUser")
    public String ExpenseByCategory(@Valid @ModelAttribute("expenseDTO") ExpenseDTO expenseDTO, Model model,
                                    Authentication authentication,
                                    BindingResult theBindingResult, HttpSession session) throws Exception {
        if (theBindingResult.hasErrors()) {
            return "AddForm";
        }
        User user = userService.findByUsername(authentication.getName());
        expenseDTO.setUser(user);
        model.addAttribute("user", user);
        List<com.personalExpenseTracker.personalExpenseTracker.entity.Expense> expenseList = expenseService.upsertCategory(expenseDTO, user);
        if (CollectionUtils.isEmpty(expenseList)) {
            // return exception or page redirection
            return "AddForm";
        }
        model.addAttribute("expense", expenseList);
        return "redirect:/";
    }


    @GetMapping("/expenses/date/{from}/{to}")
    public String ExpenseByDate(@PathVariable("from") String fromDate,@PathVariable("to") String toDate, Model model) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date1 = formatter.parse(fromDate);
        Date date2 = formatter.parse(toDate);
        List<com.personalExpenseTracker.personalExpenseTracker.entity.Expense> expenseList = expenseService.findExpensesWithinDateRange(date1,date2);
        model.addAttribute("expenses", expenseList);
        return "homePage";
    }

    @GetMapping("deleteExpense/category/{id}")
    public String deleteExpenseForm(@PathVariable("id") int id) {
        expenseService.deleteById(id);
        return "homePage";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Optional<com.personalExpenseTracker.personalExpenseTracker.entity.Expense> expense = expenseService.findById(id);
        if (expense.isPresent()) {
            model.addAttribute("expense", expense.get());
            return "UpdateForm";
        } else {
            return "redirect:/";
        }
    }
    @PostMapping("/updateExpense/category")
    public String updateExpense(@ModelAttribute("expense") Expense expense) {
        expenseService.updateExpense(expense);
        return "redirect:/";
    }

    @RequestMapping(path = "/expenseCSV")
    public void getAllExpensesInCsv(HttpServletResponse servletResponse) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"expenses.csv\"");
        csvExportService.writeExpensesToCsv(servletResponse.getWriter());
    }
}

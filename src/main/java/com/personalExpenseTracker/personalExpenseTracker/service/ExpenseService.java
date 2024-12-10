package com.personalExpenseTracker.personalExpenseTracker.service;

import com.personalExpenseTracker.personalExpenseTracker.entity.User;
import com.personalExpenseTracker.personalExpenseTracker.user.ExpenseDTO;
import com.personalExpenseTracker.personalExpenseTracker.user.Expense;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ExpenseService {
    List<com.personalExpenseTracker.personalExpenseTracker.entity.Expense> findExpensesByUser(User user);
    List<com.personalExpenseTracker.personalExpenseTracker.entity.Expense> findExpensesByUserId(int id);
    List<com.personalExpenseTracker.personalExpenseTracker.entity.Expense> findExpensesByMonth(Date date);

    List<com.personalExpenseTracker.personalExpenseTracker.entity.Expense> findExpensesByCategory(String Category);

    void save(ExpenseDTO expense);

    void deleteById(int id);

    List<com.personalExpenseTracker.personalExpenseTracker.entity.Expense> upsertCategory(ExpenseDTO expenseDTO, User user);

    Optional<com.personalExpenseTracker.personalExpenseTracker.entity.Expense> findById(int id);

    int updateExpense(Expense expense);

    List<com.personalExpenseTracker.personalExpenseTracker.entity.Expense> findExpensesWithinDateRange(
             Date startDate, Date endDate);

}

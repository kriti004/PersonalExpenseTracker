package com.personalExpenseTracker.personalExpenseTracker.service.impl;

import com.personalExpenseTracker.personalExpenseTracker.entity.User;
import com.personalExpenseTracker.personalExpenseTracker.repository.ExpenseRepository;
import com.personalExpenseTracker.personalExpenseTracker.service.ExpenseService;
import com.personalExpenseTracker.personalExpenseTracker.user.ExpenseDTO;
import com.personalExpenseTracker.personalExpenseTracker.user.Expense;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<com.personalExpenseTracker.personalExpenseTracker.entity.Expense> findExpensesByUser(User user) {
        return expenseRepository.findExpensesByUser(user);
    }

    public List<com.personalExpenseTracker.personalExpenseTracker.entity.Expense> findExpensesByUserId(int id) {
        return expenseRepository.findExpensesByUserId(id);
    }


    @Override
    public List<com.personalExpenseTracker.personalExpenseTracker.entity.Expense> findExpensesByMonth(Date date) {
        return expenseRepository.findExpensesByMonth(date);
    }

    @Override
    public List<com.personalExpenseTracker.personalExpenseTracker.entity.Expense> findExpensesByCategory(String Category) {
        return expenseRepository.findExpensesByCategory(Category);
    }

    @Override
    public void save(ExpenseDTO expense) {
        com.personalExpenseTracker.personalExpenseTracker.entity.Expense ex = new com.personalExpenseTracker.personalExpenseTracker.entity.Expense();
        ex.setCategory(expense.getCategory());
        ex.setSaving(expense.getSaving());
        ex.setExpenditure(expense.getExpenditure());
        ex.setDate(expense.getDate());
        ex.setUser(expense.getUser());
        expenseRepository.save(ex);
    }

    @Override
    public void deleteById(int id) {
        expenseRepository.deleteById(id);
    }

    public List<com.personalExpenseTracker.personalExpenseTracker.entity.Expense> upsertCategory(ExpenseDTO expenseDTO, User user) {
        try {

            List<com.personalExpenseTracker.personalExpenseTracker.entity.Expense> expenses = findExpensesByCategory(expenseDTO.getCategory());
            if (CollectionUtils.isEmpty(expenses)) {

                com.personalExpenseTracker.personalExpenseTracker.entity.Expense expense = new com.personalExpenseTracker.personalExpenseTracker.entity.Expense();

                expense.setCategory(expenseDTO.getCategory());
                expense.setExpenditure(expenseDTO.getExpenditure());
                expense.setSaving(expenseDTO.getSaving());
                expense.setDate(expenseDTO.getDate());
                expense.setUser(user);

                expenseRepository.save(expense);

                return expenseRepository.findExpensesByUser(user);
            }
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public Optional<com.personalExpenseTracker.personalExpenseTracker.entity.Expense> findById(int id) {
        return expenseRepository.findById(id);
    }

    @Override
    public int updateExpense(Expense expense) {
        return expenseRepository.updateExpense(
                expense.getId(),
                expense.getCategory(),
                expense.getExpenditure(),
                expense.getSaving(),
                expense.getDate()
        );

    }

    @Override
    public List<com.personalExpenseTracker.personalExpenseTracker.entity.Expense> findExpensesWithinDateRange(Date startDate, Date endDate) {
        return expenseRepository.findExpensesWithinDateRange(startDate, endDate);
    }



}


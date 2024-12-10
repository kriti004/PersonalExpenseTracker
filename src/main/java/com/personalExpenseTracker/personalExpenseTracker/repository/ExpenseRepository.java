package com.personalExpenseTracker.personalExpenseTracker.repository;

import com.personalExpenseTracker.personalExpenseTracker.entity.Expense;
import com.personalExpenseTracker.personalExpenseTracker.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    List<Expense> findExpensesByUser(User user);
   List<Expense> findExpensesByUserId(int id);

    @Query("SELECT e FROM Expense e WHERE MONTH(e.date) = MONTH(:date)")
    List<Expense> findExpensesByMonth(@Param("date") Date date);

    List<Expense> findExpensesByCategory(String Category);

    @Modifying
    @Transactional
    @Query("UPDATE Expense e SET e.category = :category, e.expenditure = :expenditure, e.saving = :saving, e.date = :date WHERE e.id = :id")
    int updateExpense(
            @Param("id") int id,
            @Param("category") String category,
            @Param("expenditure") int expenditure,
            @Param("saving") int saving,
            @Param("date") Date date
    );

    @Query("SELECT e FROM Expense e WHERE e.date BETWEEN :startDate AND :endDate")
    List<Expense> findExpensesWithinDateRange(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

}
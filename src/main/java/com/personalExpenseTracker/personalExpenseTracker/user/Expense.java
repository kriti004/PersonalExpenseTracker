package com.personalExpenseTracker.personalExpenseTracker.user;

import com.personalExpenseTracker.personalExpenseTracker.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Date;

@Data
public class Expense {
    @NotNull(message = "is required")
    private int id;


    @NotNull(message = "is required")
    private String category;


    @NotNull(message ="is required")
    private int expenditure;


    @NotNull(message ="is required")
    private int saving;

    @NotNull(message ="is required")
    private Date date;

    private User user;
}

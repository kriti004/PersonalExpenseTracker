package com.personalExpenseTracker.personalExpenseTracker.user;

import com.personalExpenseTracker.personalExpenseTracker.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.sql.Date;

@Data
@Validated
public class ExpenseDTO {

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

    public ExpenseDTO(String category, int expenditure, int saving, Date date, User user) {
        this.category = category;
        this.expenditure = expenditure;
        this.saving = saving;
        this.date = date;
        this.user = user;
    }

    public ExpenseDTO() {
    }
}

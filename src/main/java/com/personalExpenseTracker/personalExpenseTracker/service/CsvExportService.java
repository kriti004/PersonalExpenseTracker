package com.personalExpenseTracker.personalExpenseTracker.service;

import com.personalExpenseTracker.personalExpenseTracker.entity.Expense;
import com.personalExpenseTracker.personalExpenseTracker.repository.ExpenseRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;


@Service
public class CsvExportService {

    private static final Logger log = getLogger(CsvExportService.class);

    private final ExpenseRepository expenseRepository;

    public CsvExportService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public void writeExpensesToCsv(Writer writer) {

        List<Expense> expenselist = expenseRepository.findAll();
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord("ID", "Category", "Expenditure","Saving","Date");
            for (Expense expenses : expenselist) {
                csvPrinter.printRecord(expenses.getId(), expenses.getCategory(), expenses.getSaving(), expenses.getExpenditure(), expenses.getDate());
            }
        } catch (IOException e) {
            log.error("Error While writing CSV ", e);
        }
    }
}
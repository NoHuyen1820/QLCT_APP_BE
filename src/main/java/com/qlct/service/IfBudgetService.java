package com.qlct.service;

import com.qlct.model.Budget;

import java.util.concurrent.ExecutionException;

public interface IfBudgetService {
    Budget getBudget (String budgetCode) throws ExecutionException, InterruptedException;
  Budget getBudgets(String budgetCode);
    String createBudget(Budget budget);
    String deleteBudget(String budgetCode);
    String updateBudget(String budgetCode);

}

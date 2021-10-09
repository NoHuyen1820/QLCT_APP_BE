package com.qlct.service;

import com.qlct.core.dto.BudgetDTO;
import com.qlct.model.Budget;

import java.util.concurrent.ExecutionException;

public interface IfBudgetService {

    BudgetDTO getBudget(String budgetCode) throws ExecutionException, InterruptedException;

    Budget getBudgets(String budgetCode);

    String createBudget(BudgetDTO budget) throws ExecutionException, InterruptedException;

    String deleteBudget(String budgetCode) throws ExecutionException, InterruptedException;

    String updateBudget(String budgetCode);

}

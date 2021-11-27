package com.qlct.service;

import java.util.concurrent.ExecutionException;

public interface IfMasterService {

    String deleteTransaction(String transactionNumber) throws ExecutionException, InterruptedException;

    String deleteBudget(String budgetCode) throws ExecutionException, InterruptedException;
}

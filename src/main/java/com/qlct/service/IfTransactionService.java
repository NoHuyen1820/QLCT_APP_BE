package com.qlct.service;

import com.qlct.core.dto.TransactionDTO;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IfTransactionService {

    TransactionDTO getTransaction (String transactionNumber) throws ExecutionException, InterruptedException;

    List<TransactionDTO> getTransactions () throws ExecutionException, InterruptedException;

    String createTransaction( TransactionDTO transaction) throws ExecutionException, InterruptedException;

    String deleteTransaction(String transactionNumber) throws ExecutionException, InterruptedException;

    String updateTransaction(TransactionDTO transaction) throws ExecutionException, InterruptedException;

    List<TransactionDTO> getAllTransactionByBudgetCode ( String budgetCode) throws ExecutionException, InterruptedException;

    List<TransactionDTO> getAllTransactionByMultiBudgetCode (TransactionDTO transactionDTO) throws ExecutionException, InterruptedException;

}

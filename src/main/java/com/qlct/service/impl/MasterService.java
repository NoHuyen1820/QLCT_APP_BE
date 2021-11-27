package com.qlct.service.impl;

import com.qlct.core.dto.BudgetDTO;
import com.qlct.core.dto.TransactionDTO;
import com.qlct.service.IfMasterService;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Singleton
public class MasterService implements IfMasterService {

    @Inject
    TransactionService transactionService;

    @Inject
    BudgetService budgetService;

    @Override
    public String deleteTransaction(String transactionNumber) throws ExecutionException, InterruptedException {
        return "";
    }

    @Override
    public String deleteBudget(String budgetCode) throws ExecutionException, InterruptedException {
        log.info("BEGIN: BudgetService.deleteBudget");
        List<TransactionDTO> needDeleteTrans = transactionService.getAllTransactionByBudgetCode(budgetCode);
        for (TransactionDTO trans : needDeleteTrans) {
            transactionService.deleteTransaction(trans.getTransactionNumber());
        }

        BudgetDTO budgetDTO = new BudgetDTO();
        budgetDTO.setDeleteFlag(true);
        budgetDTO.setBudgetCode(budgetCode);
        budgetService.updateBudget(budgetDTO);

        log.info("END: BudgetService.deleteBudget");
        return "Document ID" + budgetCode + " " + "delete";
    }
}

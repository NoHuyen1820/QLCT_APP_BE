package com.qlct.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.qlct.model.Budget;
import com.qlct.service.IfBudgetService;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

@Singleton
@Slf4j
public class BudgetService implements IfBudgetService {

    @Override
    public Budget getBudget(String budgetCode) throws ExecutionException, InterruptedException {
        log.info("BEGIN: BudgetService.getBudget");
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionReference = db.collection("budget");
        Query query1 = collectionReference.whereEqualTo("budget_code", budgetCode);
        ApiFuture<QuerySnapshot> apiFuture = query1.get();
        for (DocumentSnapshot document : apiFuture.get().getDocuments()) {
            log.info(document.getId());
        Budget budget = document.toObject(Budget.class);
        }
   return null;
    }

    @Override
    public Budget getBudgets(String budgetCode) {
        return null;
    }

    @Override
    public String createBudget(Budget budget) {
        return null;
    }

    @Override
    public String deleteBudget(String budgetCode) {
        return null;
    }

    @Override
    public String updateBudget(String budgetCode) {
        return null;
    }

}
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
        Budget budget = null;

        // get firestore to query data
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionReference = db.collection("budget");
        Query query1 = collectionReference.whereEqualTo("budgetCode", budgetCode);

        // get data from query
        ApiFuture<QuerySnapshot> apiFuture = query1.get();
        // for loop data
        for (DocumentSnapshot document : apiFuture.get().getDocuments()) {
            log.info("Get data success");
            budget = document.toObject(Budget.class);
        }
        log.info("END: BudgetService.getBudget");
        return budget;
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
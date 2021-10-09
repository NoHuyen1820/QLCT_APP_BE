package com.qlct.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.qlct.core.dto.BudgetDTO;
import com.qlct.core.mapper.BudgetMapper;
import com.qlct.model.Budget;
import com.qlct.service.BaseService;
import com.qlct.service.IfBudgetService;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.concurrent.ExecutionException;

@Slf4j
@Singleton
public class BudgetService extends BaseService implements IfBudgetService {

    @Inject
    private  BudgetMapper budgetMapper;

    @Override
    public BudgetDTO getBudget(String budgetCode) throws ExecutionException, InterruptedException {
        log.info("BEGIN: BudgetService.getBudget");
        BudgetDTO result = null;

        // get firestore to query data
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionReference = db.collection("budget");
        Query query1 = collectionReference.whereEqualTo("budgetCode", budgetCode);

        // get data from query
        ApiFuture<QuerySnapshot> apiFuture = query1.get();
        // for loop data
        Budget budgetEntity;
        for (DocumentSnapshot document : apiFuture.get().getDocuments()) {
            log.info("Get data success");
            budgetEntity = document.toObject(Budget.class);
            result = budgetMapper.toDto(budgetEntity);
        }
        log.info("END: BudgetService.getBudget");
        return result;
    }

    @Override
    public Budget getBudgets(String budgetCode) {
        return null;
    }

    @Override
    public String createBudget(BudgetDTO budget) throws ExecutionException, InterruptedException {

        log.info(String.valueOf(budget.getCreatedAt()));
        log.info("BEGIN:BudgetService.createBudget");
        Firestore db = FirestoreClient.getFirestore();
        Budget budgetEntity = budgetMapper.toEntity(budget);
        ApiFuture<DocumentReference> docRef = db.collection("budget").add(budgetEntity);
        log.info("END:BudgetService.createBudget");
        return docRef.get().getId();
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
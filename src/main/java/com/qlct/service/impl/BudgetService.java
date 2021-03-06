package com.qlct.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.qlct.core.dto.BudgetDTO;
import com.qlct.core.mapper.BudgetMapper;
import com.qlct.core.util.Generate;
import com.qlct.model.Budget;
import com.qlct.service.BaseService;
import com.qlct.service.IfBudgetService;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Slf4j
@Singleton
public class BudgetService extends BaseService implements IfBudgetService {

    @Inject
    private BudgetMapper budgetMapper;

    @Override
    public BudgetDTO getBudget(String budgetCode) throws ExecutionException, InterruptedException {
        log.info("BEGIN: BudgetService.getBudget");
        BudgetDTO result = null;

        // get firestore to query data
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionReference = db.collection("budget");
        Query query = collectionReference.whereEqualTo("budgetCode", budgetCode).whereEqualTo("deleteFlag", false);

        // get data from query
        ApiFuture<QuerySnapshot> apiFuture = query.get();
        // for loop data
        Budget budgetEntity;
        for (DocumentSnapshot document : apiFuture.get().getDocuments()) {
            log.info("Get data success");
            budgetEntity = document.toObject(Budget.class);
            assert budgetEntity != null;
            budgetEntity.setId(document.getId());
            result = budgetMapper.toDto(budgetEntity);
        }
        log.info("END: BudgetService.getBudget");
        return result;
    }

    @Override
    public List<BudgetDTO> getBudgets() throws ExecutionException, InterruptedException {
        log.info("BEGIN: BudgetService.getBudgets");
        List<BudgetDTO> budgetDTOList = new ArrayList<>();

        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionReference = db.collection("budget");
        Query queryList = collectionReference.whereEqualTo("deleteFlag", false).orderBy("createdAt", Query.Direction.DESCENDING);
        ApiFuture<QuerySnapshot> apiFuture = queryList.get();

        for (DocumentSnapshot document : apiFuture.get().getDocuments()) {
            Budget budgetEntity = document.toObject(Budget.class);
            assert budgetEntity != null;
            budgetEntity.setId(document.getId());
            BudgetDTO budgetDTO = budgetMapper.toDto(budgetEntity);
            budgetDTOList.add(budgetDTO);
        }

        log.info("END: BudgetService.getBudgets");
        return budgetDTOList;
    }

    @Override
    public String createBudget(BudgetDTO budget) throws ExecutionException, InterruptedException {

        log.info(String.valueOf(budget.getCreatedAt()));
        log.info("BEGIN:BudgetService.createBudget");
        Firestore db = FirestoreClient.getFirestore();
        Budget budgetEntity = budgetMapper.toEntity(budget);
        budgetEntity.setCreatedAt(new Date());
        budgetEntity.setUpdatedAt(new Date());
        budgetEntity.setDeleteFlag(false);
        budgetEntity.setBudgetCode(Generate.generateCode("BG"));
        ApiFuture<DocumentReference> docRef = db.collection("budget").add(budgetEntity);
        log.info("END:BudgetService.createBudget");
        return docRef.get().getId();
    }

    @Override
    public String updateBudget(BudgetDTO budget) throws ExecutionException, InterruptedException {
        log.info("BEGIN:BudgetService.updateBudget");

        final String budgetCode = budget.getBudgetCode();
        if (!Objects.equals(budgetCode, "")) {

            // Check existing budget by budgetCode
            BudgetDTO budgetDTO = getBudget(budgetCode);
            if (null != budgetDTO) {
                Firestore db = FirestoreClient.getFirestore();

                // Map value to update
                Map<String, Object> map = new HashMap<>();
                if (null != budget.getAmount()) {
                    map.put("amount", budget.getAmount());
                }

                if (null != budget.getDescription()) {
                    map.put("description", budget.getDescription());
                }

                if (null != budget.getName()) {
                    map.put("name", budget.getName());
                }

                if (null != budget.getStartAt()) {
                    map.put("startAt", budget.getStartAt());
                }

                if (null != budget.getEndAt()) {
                    map.put("endAt", budget.getEndAt());
                }

                if (null != budget.getPassword()) {
                    map.put("password", budget.getPassword());
                }

                if (budgetDTO.isDeleteFlag() != budget.isDeleteFlag()) {
                    map.put("deleteFlag", budget.isDeleteFlag());
                }

                if (budgetDTO.getType() != budget.getType()) {
                    map.put("type", budget.getType());
                }

                if (budgetDTO.getStatus() != budget.getStatus()) {
                    map.put("status", budget.getStatus());
                }

                if (budgetDTO.getColor() != budget.getColor()) {
                    map.put("color", budget.getColor());
                }

                // Check map has value or not
                if (map.isEmpty()) {
                    log.info("no change");
                } else {
                    map.put("updatedAt", new Date());
                    ApiFuture<WriteResult> writeResult = db.collection("budget").document(budgetDTO.getId()).update(map);
                    log.info("END:BudgetService.updateBudget");
                    return writeResult.get().toString();
                }

            } else {
                log.info("budget is not exist");
            }

        } else {
            log.info("budgetCode cannot be null");
        }

        log.info("Cannot update budget.");
        return null;
    }

    @Override
    public String deleteBudget(String budgetCode) throws ExecutionException, InterruptedException {

        log.info("BEGIN:BudgetService.deleteBudget");
        BudgetDTO budgetDTO = new BudgetDTO();
        budgetDTO.setDeleteFlag(true);
        budgetDTO.setBudgetCode(budgetCode);
        updateBudget(budgetDTO);

        log.info("END:BudgetService.deleteBudget");
        return "Document ID" + budgetCode + " " + "delete";
    }

    // getAllBudget by userCode
    @Override
    public List<BudgetDTO> getAllBudgetByUserCode(String userCode) throws ExecutionException, InterruptedException {
        log.info("BEGIN: BudgetService.getAllBudgetByUserCode");
        List<BudgetDTO> budgetDTOList = new ArrayList<>();

        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionReference = db.collection("budget");
        Query queryList = collectionReference.whereEqualTo("deleteFlag", false).whereEqualTo("userCode", userCode);
        ApiFuture<QuerySnapshot> apiFutureNew = queryList.get();

        for (DocumentSnapshot document : apiFutureNew.get().getDocuments()) {
            Budget budgetEntity = document.toObject(Budget.class);
            assert budgetEntity != null;
            budgetEntity.setId(document.getId());
            BudgetDTO budgetDTO = budgetMapper.toDto(budgetEntity);
            budgetDTOList.add(budgetDTO);
        }
        log.info("END: BudgetService.getAllBudgetByUserCode");
        return budgetDTOList;
    }


}
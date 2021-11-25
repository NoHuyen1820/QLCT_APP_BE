package com.qlct.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.qlct.core.dto.BudgetDTO;
import com.qlct.core.dto.TransactionDTO;
import com.qlct.core.mapper.TransactionMapper;
import com.qlct.core.util.Generate;
import com.qlct.model.Transaction;
import com.qlct.service.IfTransactionService;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Slf4j
@Singleton
public class TransactionService implements IfTransactionService {

    @Inject
    private TransactionMapper transactionMapper;

    @Inject
    BudgetService budgetService;

    @Override
    public TransactionDTO getTransaction(String transactionNumber) throws ExecutionException, InterruptedException {
        log.info("BEGIN: TransactionService.getTransaction");
        TransactionDTO result = null;
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionReference = db.collection("transaction");
        Query query = collectionReference.whereEqualTo("transactionNumber", transactionNumber).whereEqualTo("deleteFlag", false);
        ApiFuture<QuerySnapshot> apiFuture = query.get();

        Transaction transactionEntity;
        for (DocumentSnapshot document : apiFuture.get().getDocuments()) {
            log.info("Get data success");
            transactionEntity = document.toObject(Transaction.class);
            assert transactionEntity != null;
            transactionEntity.setId(document.getId());
            result = transactionMapper.toDto(transactionEntity);

        }
        log.info("END: TransactionService.getTransaction");
        return result;
    }


    @Override
    public List<TransactionDTO> getTransactions() throws ExecutionException, InterruptedException {
        log.info("BEGIN: TransactionService.getTransactions");
        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionReference = db.collection("transaction");
        Query queryList = collectionReference.whereEqualTo("deleteFlag", false).orderBy("createdAt", Query.Direction.DESCENDING);
        ApiFuture<QuerySnapshot> apiFuture = queryList.get();

        for (DocumentSnapshot document : apiFuture.get().getDocuments()) {
            Transaction transactionEntity = document.toObject(Transaction.class);
            assert transactionEntity != null;
            transactionEntity.setId(document.getId());
            TransactionDTO transactionDTO = transactionMapper.toDto(transactionEntity);
            transactionDTOList.add(transactionDTO);
        }

        log.info("END: TransactionService.getTransactions");
        return transactionDTOList;
    }

    @Override
    public String createTransaction(TransactionDTO transaction) throws ExecutionException, InterruptedException {
        log.info("BEGIN:TransactionService.createTransaction");
        Firestore db = FirestoreClient.getFirestore();
        Transaction transactionEntity = transactionMapper.toEntity(transaction);
        transactionEntity.setCreatedAt(new Date());
        transactionEntity.setUpdatedAt(new Date());
        transactionEntity.setDeleteFlag(false);
        transactionEntity.setTransactionNumber(Generate.generateCode("TR"));
        ApiFuture<DocumentReference> docRef = db.collection("transaction").add(transactionEntity);

        String budgetCode = transactionEntity.getBudgetCode();
        BudgetDTO currentBudget = budgetService.getBudget(budgetCode);
        if(null != currentBudget){

            BigDecimal currentAmount = currentBudget.getAmount();
            BigDecimal amountTrans = transactionEntity.getAmount();
            if (transactionEntity.getType() == 0) {
                currentAmount = currentAmount.add(amountTrans);
            }
            if (transactionEntity.getType() == 1) {
                currentAmount = currentAmount.subtract(amountTrans);
            }
            currentBudget.setAmount(currentAmount);
            budgetService.updateBudget(currentBudget);

        }
        // after add transaction success
        // Dung butgetCode de get budget cu~ tren firebase
        // => BudgetDTO currentBudget = BudgetService.getBudget(transaction.budgetCode)

        // cap nhat lai amount cho: currentAmount = currentBudget.getAmount
        // tuy theo transaction  type la 0 hay 1 de cong hoac tru amount.
        // currentAmount = currentAmount (+/-) transaction.amount
        // currentBudget.setAmount(currentAmount)
        // go
        // sau khi da~ cap nhat amount cua budget r
        // call ham updateBudget(newBudget) => ok? ok

        log.info("END:TransactionService.createTransaction");
        return docRef.get().getId();
    }

    @Override
    public String deleteTransaction(String transactionNumber) throws ExecutionException, InterruptedException {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setDeleteFlag(true);
        transactionDTO.setTransactionNumber(transactionNumber);
        updateTransaction(transactionDTO);
        return "Transaction ID " + transactionNumber + " " + "delete";
    }

    @Override
    public String updateTransaction(TransactionDTO transaction) throws ExecutionException, InterruptedException {
        log.info("BEGIN: TransactionService.updateTransaction");
        final String transactionNumber = transaction.getTransactionNumber();
        if (!Objects.equals(transactionNumber, "")) {
            TransactionDTO transactionDTO = getTransaction(transactionNumber);
            if (null != transactionDTO) {
                Firestore db = FirestoreClient.getFirestore();

                Map<String, Object> map = new HashMap<>();

                if (null != transaction.getTransactionName()) {
                    map.put("transactionName", transaction.getTransactionName());
                }

                if (null != transaction.getAmount()) {
                    map.put("amount", transaction.getAmount());
                }

                if (null != transaction.getNote()) {
                    map.put("note", transaction.getNote());
                }

                if (null != transaction.getStartAt()) {
                    map.put("startAt", transaction.getStartAt());
                }

                if ((null != transaction.getEndAt())) {
                    map.put("endAt", transaction.getEndAt());
                }

                if ((transactionDTO.getCategory() != transaction.getCategory())) {
                    map.put("category", transaction.getCategory());
                }

                if (transactionDTO.getStatus() != transaction.getStatus()) {
                    map.put("status", transaction.getStatus());
                }

                if (transactionDTO.getPayment() != transaction.getPayment()) {
                    map.put("payment", transaction.getPayment());
                }

                if (transactionDTO.isSchedule() != transaction.isSchedule()) {
                    map.put("isSchedule", transaction.isSchedule());
                }

                if ((transactionDTO.isDeleteFlag() != transaction.isDeleteFlag())) {
                    map.put("deleteFlag", transaction.isDeleteFlag());
                }

                if (map.isEmpty()) {
                    log.info(" data no change");
                } else {
                    map.put("updatedAt", new Date());
                    ApiFuture<WriteResult> writeResultApiFuture = db.collection("transaction").document(transactionDTO.getId()).update(map);
                    log.info("END: TransactionService.updateTransaction");
                    return writeResultApiFuture.get().toString();
                }

            } else {
                log.info("transaction is not exist");
            }
        } else {
            log.info("transactionNumber cannot be null");
        }

        log.info("Cannot update transaction");
        return null;
    }

    @Override
    public List<TransactionDTO> getAllTransactionByBudgetCode(String budgetCode) throws ExecutionException, InterruptedException {
        log.info("BEGIN: TransactionService.getAllTransactionByBudgetCode");
        List<TransactionDTO> transactionDTOListNew = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionReference = db.collection("transaction");
        Query queryList = collectionReference.whereEqualTo("deleteFlag", false).whereEqualTo("budgetCode",budgetCode).orderBy("createdAt", Query.Direction.DESCENDING);
        ApiFuture<QuerySnapshot> apiFuture = queryList.get();

        for (DocumentSnapshot document : apiFuture.get().getDocuments()) {
            Transaction transactionEntity = document.toObject(Transaction.class);
            assert transactionEntity != null;
            transactionEntity.setId(document.getId());
            TransactionDTO transactionDTO = transactionMapper.toDto(transactionEntity);
            transactionDTOListNew.add(transactionDTO);
        }

        log.info("END: TransactionService.getAllTransactionByBudgetCode");
        return transactionDTOListNew;
    }

    @Override
    public List<TransactionDTO> getAllTransactionByMultiBudgetCode(TransactionDTO transactionDTO) throws ExecutionException, InterruptedException {
        log.info("BEGIN: TransactionService.getAllTransactionByMultiBudgetCode");
        List<TransactionDTO> transactionDTOListNew = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionReference = db.collection("transaction");
        Query queryList = collectionReference.whereEqualTo("deleteFlag", false)
                .whereIn("budgetCode", transactionDTO.getBudgetCodes())
                .whereGreaterThanOrEqualTo("createdAt", transactionDTO.getFromDate())
                .whereLessThanOrEqualTo("createdAt", transactionDTO.getToDate());
        ApiFuture<QuerySnapshot> apiFuture = queryList.get();

        for (DocumentSnapshot document : apiFuture.get().getDocuments()) {
            Transaction transactionEntity = document.toObject(Transaction.class);
            assert transactionEntity != null;
            transactionEntity.setId(document.getId());
            TransactionDTO transDTO = transactionMapper.toDto(transactionEntity);
            transactionDTOListNew.add(transDTO);
        }

        log.info("END: TransactionService.getAllTransactionByMultiBudgetCode");
        return transactionDTOListNew;
    }
}

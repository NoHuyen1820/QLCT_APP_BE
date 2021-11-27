package com.qlct.controller;

import com.qlct.core.dto.TransactionDTO;
import com.qlct.core.dto.api.ResponseDTO;
import com.qlct.service.IfTransactionService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Slf4j
@Controller("/transaction")
public class TransactionController {

    @Inject
    IfTransactionService transactionService;

    @Get("/getTransaction")
    public ResponseDTO<TransactionDTO> getTransaction(@QueryValue String transactionNumber) throws ExecutionException, InterruptedException {
        ResponseDTO<TransactionDTO> responseDTO = new ResponseDTO<>();
        if (Objects.equals(transactionNumber, "")) {
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        } else {
            TransactionDTO transactionDTO = transactionService.getTransaction(transactionNumber);
            responseDTO.setData(transactionDTO);
        }
        return responseDTO;
    }

    @Get("/getTransactions")
    public ResponseDTO<List<TransactionDTO>> getTransactions() throws ExecutionException, InterruptedException {
        ResponseDTO<List<TransactionDTO>> responseDTO = new ResponseDTO<>();
        List<TransactionDTO> transactionDTOList = transactionService.getTransactions();
        responseDTO.setData(transactionDTOList);
        return responseDTO;
    }

    @Post("/createTransaction")
    public ResponseDTO<String> createTransaction(@Body TransactionDTO transaction) throws ExecutionException, InterruptedException {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if (null == transaction) {
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        } else {
            String transactionNumber = transactionService.createTransaction(transaction);
            responseDTO.setData(transactionNumber);
        }

        return responseDTO;
    }

    @Put("/updateTransaction")
    public ResponseDTO<String> updateTransaction(@Body TransactionDTO transaction) throws ExecutionException, InterruptedException {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if (null == transaction) {
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        } else {
            String transactionNumber = transactionService.updateTransaction(transaction);
            responseDTO.setData(transactionNumber);
        }

        return responseDTO;
    }

    @Post("/deleteTransaction")
    public ResponseDTO<String> deleteTransaction(@QueryValue String transactionNumber) throws ExecutionException, InterruptedException {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if (null == transactionNumber) {
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        } else {
            String transactionDel = transactionService.deleteTransaction(transactionNumber);
            responseDTO.setData(transactionDel);
        }
        return responseDTO;
    }

    @Post("/getAllTransactionByMultiBudgetCode")
    public ResponseDTO<List<TransactionDTO>> getAllTransactionByMultiBudgetCode(@Body TransactionDTO transactionDTO) throws ExecutionException, InterruptedException {
        ResponseDTO<List<TransactionDTO>> responseDTO = new ResponseDTO<>();
        List<TransactionDTO> transactionDTOList = transactionService.getAllTransactionByMultiBudgetCode(transactionDTO);
        responseDTO.setData(transactionDTOList);
        return responseDTO;
    }

    @Get("/getAllTransactionByBudgetCode")
    public ResponseDTO<List<TransactionDTO>> getAllTransactionByBudgetCode(@QueryValue String budgetCode) throws ExecutionException, InterruptedException {
        ResponseDTO<List<TransactionDTO>> responseDTO = new ResponseDTO<>();
        List<TransactionDTO> transactionDTOList = transactionService.getAllTransactionByBudgetCode(budgetCode);
        responseDTO.setData(transactionDTOList);
        return responseDTO;
    }

}

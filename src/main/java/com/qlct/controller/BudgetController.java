package com.qlct.controller;

import com.qlct.core.dto.BudgetDTO;
import com.qlct.core.dto.api.ResponseDTO;
import com.qlct.service.IfBudgetService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Slf4j
@Controller("/budget")
public class BudgetController {

    @Inject
    IfBudgetService budgetService;

    @Get("/getBudget")
    public ResponseDTO<BudgetDTO> getBudget(@QueryValue String budgetCode) throws ExecutionException, InterruptedException {
        log.info("BEGIN: BudgeController.getBudget");
        ResponseDTO<BudgetDTO> responseDTO = new ResponseDTO<>();
        if(Objects.equals(budgetCode,"")){
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        } else{
            BudgetDTO budget = budgetService.getBudget(budgetCode);
            responseDTO.setData(budget);
        }
        log.info("END: BudgeController.getBudget");
        return responseDTO;
    }

    @Get("/getBudgets")
    public ResponseDTO<List<BudgetDTO>> getBudgets() throws ExecutionException, InterruptedException {
        ResponseDTO<List<BudgetDTO>> responseDTO = new ResponseDTO<>();
        List<BudgetDTO> budgetDTOList = budgetService.getBudgets();
        responseDTO.setData(budgetDTOList);
        return responseDTO;
    }

    @Post("/createBudget")
    public ResponseDTO<String> createBudget(@Body BudgetDTO budget) throws ExecutionException, InterruptedException {
        log.info("BEGIN: BudgeController.createBudget");
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if(null == budget){
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        } else{
            String budgetID = budgetService.createBudget(budget);
            responseDTO.setData(budgetID);
        }

        log.info("END: BudgeController.createBudget");
        return responseDTO;
    }

    @Put("/updateBudget")
    public ResponseDTO<String> updateBudget(@Body BudgetDTO budget) throws ExecutionException, InterruptedException {
        log.info("BEGIN: BudgeController.updateBudget");
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if(null == budget){
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        } else{
            String budgetID = budgetService.updateBudget(budget);
            responseDTO.setData(budgetID);
        }
        log.info("END: BudgeController.updateBudget");
        return responseDTO;
    }

    @Delete("/deleteBudget")
     public  ResponseDTO<String> deleteBudget (@QueryValue String budgetCode) throws ExecutionException, InterruptedException {
        log.info("BEGIN: BudgeController.deleteBudget");
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if(null == budgetCode){
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        } else{
            String budgetDel = budgetService.deleteBudget(budgetCode);
            responseDTO.setData(budgetDel);
        }
        log.info("END: BudgeController.deleteBudget");
        return responseDTO;
     }

    @Get("/getAllBudgetByUserCode")
    public ResponseDTO<List<BudgetDTO>> getAllBudgetByUserCode(@QueryValue String userCode) throws ExecutionException, InterruptedException {
        ResponseDTO<List<BudgetDTO>> responseDTO = new ResponseDTO<>();
        List<BudgetDTO> budgetDTOList = budgetService.getAllBudgetByUserCode(userCode);
        responseDTO.setData(budgetDTOList);
        return responseDTO;
    }

}

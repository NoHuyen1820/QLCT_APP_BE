package com.qlct.controller;

import com.qlct.core.dto.api.ResponseDTO;
import com.qlct.model.Budget;
import com.qlct.service.impl.BudgetService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Slf4j
@Controller("/budget")
public class BudgetController {

    @Inject
    BudgetService budgetService;

    @Get("/getBudget")
    public ResponseDTO<Budget> getBudget(@QueryValue String budgetCode) throws ExecutionException, InterruptedException {
        log.info("BEGIN: BudgeController.getBudget");
        ResponseDTO<Budget> responseDTO1 = new ResponseDTO<>();
        if(Objects.equals(budgetCode,"")){
            responseDTO1.setStatus(HttpStatus.BAD_REQUEST);
        } else{
            Budget budget = budgetService.getBudget(budgetCode);
            responseDTO1.setData(budget);
        }
        log.info("END: BudgeController.getBudget");
        return responseDTO1;
    }

}

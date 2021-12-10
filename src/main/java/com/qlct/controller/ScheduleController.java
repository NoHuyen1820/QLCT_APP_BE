package com.qlct.controller;

import com.qlct.core.dto.ScheduleDTO;
import com.qlct.core.dto.TransactionDTO;
import com.qlct.core.dto.api.ResponseDTO;
import com.qlct.service.IfScheduleService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Slf4j
@Controller("/schedule")
public class ScheduleController {

    @Inject
    IfScheduleService scheduleService;

    @Get("/getSchedule")
    public ResponseDTO<ScheduleDTO> getSchedule(@QueryValue String scheduleId) throws ExecutionException, InterruptedException {
        ResponseDTO<ScheduleDTO> responseDTO = new ResponseDTO<>();
        if (Objects.equals(scheduleId, "")) {
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        } else {
            ScheduleDTO scheduleDTO = scheduleService.getSchedule(scheduleId);
            responseDTO.setData(scheduleDTO);
        }
        return responseDTO;
    }

    @Post("/createSchedule")
    public ResponseDTO<String> createSchedule(@Body ScheduleDTO scheduleDTO) throws ExecutionException, InterruptedException {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if (null == scheduleDTO) {
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        }else {
            String transactionNumber = scheduleService.createSchedule(scheduleDTO);
            responseDTO.setData(transactionNumber);
        }
        return responseDTO;
    }

    @Post("/deleteSchedule")
    public ResponseDTO<String> deleteSchedule(@QueryValue String scheduleId) throws ExecutionException, InterruptedException {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if (null == scheduleId) {
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        } else {
            String transactionDel = scheduleService.deleteSchedule(scheduleId);
            responseDTO.setData(transactionDel);
        }
        return responseDTO;
    }

}

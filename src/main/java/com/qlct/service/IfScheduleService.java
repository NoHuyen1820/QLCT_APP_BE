package com.qlct.service;

import com.qlct.core.dto.ScheduleDTO;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IfScheduleService {

    ScheduleDTO getSchedule(String scheduleId) throws ExecutionException, InterruptedException;

    String createSchedule(ScheduleDTO schedule) throws ExecutionException, InterruptedException;

    String deleteSchedule(String scheduleId) throws ExecutionException, InterruptedException;

    List<ScheduleDTO> getSchedules() throws ExecutionException, InterruptedException;

    void scanScheduledTransaction() throws ExecutionException, InterruptedException;
}

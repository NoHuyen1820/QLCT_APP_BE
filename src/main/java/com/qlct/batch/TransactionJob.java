package com.qlct.batch;

import com.qlct.service.IfScheduleService;
import io.micronaut.scheduling.annotation.Scheduled;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.ExecutionException;

@Slf4j
@Singleton
public class TransactionJob {

    @Inject
    IfScheduleService scheduleService;

    @Scheduled(cron = "0 50 7 ? * *") // temp : 0/20 * * ? * *  || main: 0 50 7 ? * *
    protected void runScheduled() throws ExecutionException, InterruptedException {
        log.info("BEGIN:TransactionJob.runScheduled");
        scheduleService.scanScheduledTransaction();
        log.info("END:TransactionJob.runScheduled");
    }
}

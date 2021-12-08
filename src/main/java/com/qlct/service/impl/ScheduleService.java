package com.qlct.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.qlct.core.dto.ScheduleDTO;
import com.qlct.core.dto.TransactionDTO;
import com.qlct.core.mapper.ScheduleMapper;
import com.qlct.core.util.Generate;
import com.qlct.model.Schedule;
import com.qlct.service.BaseService;
import com.qlct.service.IfScheduleService;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Slf4j
@Singleton
public class ScheduleService extends BaseService implements IfScheduleService {

    @Inject
    ScheduleMapper scheduleMapper;

    @Inject
    TransactionService transactionService;

    @Override
    public String createSchedule(ScheduleDTO schedule) throws ExecutionException, InterruptedException {
        log.info("BEGIN:ScheduleService.createSchedule");
        Firestore db = FirestoreClient.getFirestore();
        Schedule scheduleEntity = scheduleMapper.toEntity(schedule);
        scheduleEntity.setCreatedAt(new Date());
        scheduleEntity.setUpdatedAt(new Date());
        scheduleEntity.setDeleteFlag(false);
        scheduleEntity.setScheduleCode(Generate.generateCode("SC"));
        ApiFuture<DocumentReference> docRef = db.collection("schedule").add(scheduleEntity);
        log.info("END:ScheduleService.createSchedule");
        return docRef.get().getId();
    }

    @Override
    public String deleteSchedule(String scheduleId) throws ExecutionException, InterruptedException {
        log.info("BEGIN: ScheduleService.deleteSchedule: scheduleId=" + scheduleId);
        if (!Objects.equals(scheduleId, "")) {
            ScheduleDTO currentSchedule = getSchedule(scheduleId);
            if (currentSchedule != null) {
                Firestore db = FirestoreClient.getFirestore();
                Map<String, Object> map = new HashMap<>();
                map.put("deleteFlag", true);
                map.put("updatedAt", new Date());
                ApiFuture<WriteResult> writeResultApiFuture = db.collection("schedule").document(currentSchedule.getId()).update(map);
                log.info("BEGIN: ScheduleService.deleteSchedule");
                return writeResultApiFuture.get().toString();
            } else {
                log.error("CANNOT GET SCHEDULE");
            }
        }
        log.error("END: ScheduleService.deleteSchedule");
        return "";
    }

    public ScheduleDTO getSchedule(String scheduleId) throws ExecutionException, InterruptedException {
        log.info("BEGIN: ScheduleService.getSchedule");
        ScheduleDTO result = null;
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionReference = db.collection("schedule");
        Query query = collectionReference.whereEqualTo("scheduleId", Integer.valueOf(scheduleId)).whereEqualTo("deleteFlag", false);
        ApiFuture<QuerySnapshot> apiFuture = query.get();

        Schedule scheduleEntity;
        for (DocumentSnapshot document : apiFuture.get().getDocuments()) {
            log.info("Get data success");
            scheduleEntity = document.toObject(Schedule.class);
            assert scheduleEntity != null;
            scheduleEntity.setId(document.getId());
            result = scheduleMapper.toDto(scheduleEntity);
            // TODO CANNOT GET

        }
        log.info("END: ScheduleService.getSchedule");
        return result;
    }

    @Override
    public List<ScheduleDTO> getSchedules() throws ExecutionException, InterruptedException {
        log.info("BEGIN: ScheduleService.getSchedules");
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionReference = db.collection("schedule");
        Query queryList = collectionReference.whereEqualTo("deleteFlag", false).orderBy("createdAt", Query.Direction.DESCENDING);
        ApiFuture<QuerySnapshot> apiFuture = queryList.get();

        for (DocumentSnapshot document : apiFuture.get().getDocuments()) {
            Schedule scheduleEntity = document.toObject(Schedule.class);
            assert  scheduleEntity != null;
            scheduleEntity.setId(document.getId());
            ScheduleDTO scheduleDTO = scheduleMapper.toDto(scheduleEntity);
            scheduleDTOList.add(scheduleDTO);
        }

        log.info("END: ScheduleService.getSchedules");
        return scheduleDTOList;
    }

    @Override
    public void scanScheduledTransaction() throws ExecutionException, InterruptedException {
        log.info("BEGIN: ScheduleService.scanScheduledTransaction");

        List<ScheduleDTO> scheduleDTOList = getSchedules();
        Calendar calendar = Calendar.getInstance();
        for (ScheduleDTO schedule : scheduleDTOList) {
            int dayOfWeek = schedule.getDayOfWeek();
            schedule.setDayOfWeek(dayOfWeek + 1);
            if (schedule.getDayOfWeek() == 8) schedule.setDayOfWeek(1);

            if ((schedule.getHour() != -1) ||
                (schedule.getDayOfWeek() == calendar.get(Calendar.DAY_OF_WEEK)) ||
                (schedule.getDayOfMonth() == calendar.get(Calendar.DAY_OF_MONTH))) {
                TransactionDTO transDTO = new TransactionDTO();
                transDTO.setAmount(schedule.getAmount());
                transDTO.setBudgetCode(schedule.getBudgetCode());
                transDTO.setUserCode(schedule.getUserCode());
                transDTO.setCategory(schedule.getCategory());
                transDTO.setType(schedule.getType());
                transDTO.setNote(schedule.getNote());
                transactionService.createTransaction(transDTO);
            }
        }

        log.info("END: ScheduleService.scanScheduledTransaction");
    }


}

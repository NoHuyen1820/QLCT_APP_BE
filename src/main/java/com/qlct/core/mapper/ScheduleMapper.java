package com.qlct.core.mapper;

import com.qlct.core.dto.ScheduleDTO;
import com.qlct.model.Schedule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jsr330")
public interface ScheduleMapper extends EntityMapper<Schedule, ScheduleDTO>{

    @Override
    Schedule toEntity(ScheduleDTO dto);

    @Override
    ScheduleDTO toDto(Schedule entity);
}

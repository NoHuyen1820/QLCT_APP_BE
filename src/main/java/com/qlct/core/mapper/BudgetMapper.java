package com.qlct.core.mapper;

import com.qlct.core.dto.BudgetDTO;
import com.qlct.model.Budget;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jsr330")
public interface BudgetMapper extends EntityMapper<Budget, BudgetDTO> {

    @Override
    Budget toEntity(BudgetDTO dto);

    @Override
    BudgetDTO toDto(Budget entity);

}

package com.qlct.core.mapper;

import com.qlct.core.dto.TransactionDTO;
import com.qlct.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jsr330")
public interface TransactionMapper extends EntityMapper<Transaction, TransactionDTO> {

    @Override
    Transaction toEntity(TransactionDTO dto);

    @Override
    TransactionDTO toDto(Transaction entity);
}

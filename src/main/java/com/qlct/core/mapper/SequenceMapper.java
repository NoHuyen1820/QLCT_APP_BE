package com.qlct.core.mapper;

import com.qlct.core.dto.SequenceDTO;
import com.qlct.model.Sequence;
import org.mapstruct.Mapper;

@Mapper( componentModel = "jsr330")
public interface SequenceMapper  extends EntityMapper<Sequence,SequenceDTO>{

    @Override
    Sequence toEntity (SequenceDTO dto);

    @Override
    SequenceDTO toDto( Sequence entity);
}

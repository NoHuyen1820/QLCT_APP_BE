package com.qlct.service;

import com.qlct.core.dto.TypeDTO;

import java.util.concurrent.ExecutionException;

public interface IfTypeService {
    String createType (TypeDTO type) throws ExecutionException, InterruptedException;
}

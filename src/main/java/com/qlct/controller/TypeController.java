package com.qlct.controller;

import com.qlct.core.dto.TypeDTO;
import com.qlct.core.dto.api.ResponseDTO;
import com.qlct.service.IfTypeService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.concurrent.ExecutionException;
@Slf4j
@Controller("/type")
public class TypeController {
    @Inject
    IfTypeService typeService;

    @Post("/createType")
    public ResponseDTO<String> createType (@Body TypeDTO type) throws ExecutionException, InterruptedException {
        log.info("BEGIN: TypeController.createType");
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if (null == type){
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        } else {
            String typeID = typeService.createType(type);
            responseDTO.setData(typeID);
        }
        log.info("END: TypeController.createType");
        return responseDTO;
    }
}

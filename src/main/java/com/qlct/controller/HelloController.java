package com.qlct.controller;

import com.qlct.core.dto.api.ResponseDTO;
import com.qlct.model.User;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;

@Controller("/hello")
public class HelloController {
    @Get(produces = MediaType.TEXT_PLAIN)
    public ResponseDTO sayHello(@QueryValue String name) {

        ResponseDTO responseDTO = new ResponseDTO();

        if (name == "") {
            responseDTO.setData(null);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        } else {
            User user = new User();
            user.setName(name);
            user.setAge(22);
            responseDTO.setData(user);
            responseDTO.setStatus(HttpStatus.OK);
        }
        return responseDTO;
    }
}
package com.qlct.controller;

import com.qlct.core.dto.api.ResponseDTO;
import com.qlct.model.User;
import com.qlct.service.impl.UserService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Slf4j
@Controller("/user")
public class UserController {

    @Inject
    UserService userService;

    @Get("/getUsers")
    public ResponseDTO<User> getUser(@QueryValue String name) throws ExecutionException, InterruptedException {
        log.info("BEGIN: UserController.getUser");
        ResponseDTO<User> responseDTO = new ResponseDTO<>();

        if(Objects.equals(name, "")){
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        } else {
            User user = userService.getUser(name);
            responseDTO.setData(user);
            responseDTO.setStatus(HttpStatus.OK);
        }

        log.info("END: UserController.getUser");
        return responseDTO;
    }

    @Post("/createUser")
    public String createUser(@Body User user) throws ExecutionException, InterruptedException {
        return userService.saveUser(user);
    }

    @Put("/updateUser")
    public String updateUser(@Body User user) {
        return "Update user" + user.getName();
    }

    @Delete("/deleteUser")
    public String deleteUser(@QueryValue String name) {
        return userService.deleteUser(name);
    }

}

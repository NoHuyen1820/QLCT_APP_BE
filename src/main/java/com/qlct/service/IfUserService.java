package com.qlct.service;

import com.qlct.model.User;

import java.util.concurrent.ExecutionException;

public interface IfUserService {

    User getUser(String name) throws ExecutionException, InterruptedException;

    User getUsers(String name);

    String saveUser(User user) throws ExecutionException, InterruptedException;

    String deleteUser(String name);
}

package com.qlct.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Budget {

    private String budgetCode;

    private String userCode;

    private String password;

    private String description;

    private String name;

    private int color;

    private double amount;

    private int status;

    private int type;

    private int deleteFlag;

    private Date updatedAt;

    private Date createdAt;

    private Date startAt;

    private Date endAt;
}

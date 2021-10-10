package com.qlct.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class Budget {

    private  String id;

    private String budgetCode;

    private String userCode;

    private String password;

    private String description;

    private String name;

    private int color;

    private BigDecimal amount;

    private int status;

    private int type;

    private boolean deleteFlag;

    private Date updatedAt;

    private Date createdAt;

    private Date startAt;

    private Date endAt;
}

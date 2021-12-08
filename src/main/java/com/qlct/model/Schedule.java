package com.qlct.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class Schedule {

    private String id;

    private int scheduleId;

    private String scheduleCode;

    private int hour;

    private int dayOfWeek;

    private int dayOfMonth;

    private BigDecimal amount;

    private String transactionNumber;

    private String budgetCode;

    private String userCode;

    private int category;

    private int type;

    private String note;

    private  String transactionName;

    private boolean deleteFlag;

    private Date updatedAt;

    private Date createdAt;

    private Date startAt;

    private Date endAt;
}

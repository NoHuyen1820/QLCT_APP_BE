package com.qlct.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class Transaction {

     private String id;

     private String transactionNumber;

     private String budgetCode;

     private String userCode;

     private BigDecimal amount;

     private int category;

     private boolean isSchedule;

     private int type;

     private String note;

     private  String transactionName;

     private int payment;

     private int status;

     private boolean deleteFlag;

     private Date updatedAt;

     private Date createdAt;

     private Date startAt;

     private Date scheduledAt;

     private Date endAt;

}

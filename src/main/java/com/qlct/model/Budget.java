package com.qlct.model;
import com.google.cloud.Timestamp;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    private Timestamp updatedAt;
    private Timestamp createdAt;
    private Timestamp startAt;
    private Timestamp end_At;
}

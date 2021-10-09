package com.qlct.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class BudgetDTO implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private Date updatedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private Date startAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private Date endAt;

}

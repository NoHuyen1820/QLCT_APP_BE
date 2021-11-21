package com.qlct.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class TransactionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private  String id;

    private String transactionNumber;

    private String budgetCode;

    private List<String> budgetCodes;

    private String userCode;

    private BigDecimal amount;

    private int category;

    private boolean isSchedule;

    private String note;

    private  String transactionName;

    private int type;

    private int payment;

    private int status;

    private boolean deleteFlag;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private Date updatedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private  Date startAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private Date endAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private Date fromDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private Date toDate;

}

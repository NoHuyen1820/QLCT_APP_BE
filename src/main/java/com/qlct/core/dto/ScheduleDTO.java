package com.qlct.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class ScheduleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private Date updatedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private Date startAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private Date endAt;

}

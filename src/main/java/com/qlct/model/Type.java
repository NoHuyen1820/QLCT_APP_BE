package com.qlct.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Type {

    private String typeClass;

    private int typeCode;

    private String typeName;

    private String typeCluster;

    private Date updatedAt;
}

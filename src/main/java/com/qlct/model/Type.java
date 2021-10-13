package com.qlct.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

@Getter
@Setter
@ToString
public class Type {

    private int type_class;

    private String type_code;

    private String type_name;

    private String type_name_en;

    private Date updated_at;
}

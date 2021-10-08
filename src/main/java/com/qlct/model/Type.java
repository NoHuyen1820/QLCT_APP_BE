package com.qlct.model;

import com.google.cloud.Timestamp;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Type {
    private int type_class;
    private String type_code;
    private String type_name;
    private String type_name_en;
    private Timestamp updated_at;
}

package com.sccs.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EnrollTable {
    
    private Integer enrollId;
    private String studentName;
    private String className;
    private String employeeName;
}

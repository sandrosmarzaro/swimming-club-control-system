package com.sccs.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Enroll implements Serializable {

    private Integer id;
    private Integer classId;
    private Integer employeeId;
    private Integer studentId;
}

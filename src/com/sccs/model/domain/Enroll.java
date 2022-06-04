package com.sccs.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Enroll implements Serializable {
    
    private Integer id;
    @NonNull
    private Integer classId;
    @NonNull
    private Integer employeeId;
    @NonNull
    private Integer studentId;
}

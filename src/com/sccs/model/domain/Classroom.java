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
public class Classroom implements Serializable {

    private Integer id;
    private String name;
    private Integer poolId;
    private Integer vacanciesNumber;
    private Boolean enrollmentOpen;
    private Integer teacherId;
    private DayOfTheWeek dayOfTheWeek;

    @Override
    public String toString() {
        return this.name;
    }
    
    public Classroom (
            String name, 
            Integer poolId,
            Integer vacanciesNumber,
            Boolean enrollmentOpen, 
            Integer teacherId, 
            DayOfTheWeek dayOfTheWeek
        ) {
            this.name = name;
            this.poolId = poolId;
            this.vacanciesNumber = vacanciesNumber;
            this.enrollmentOpen = enrollmentOpen;
            this.teacherId = teacherId;
            this.dayOfTheWeek = dayOfTheWeek;
    }
}

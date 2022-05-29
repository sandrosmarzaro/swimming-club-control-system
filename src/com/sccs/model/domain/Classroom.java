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
    private Boolean enrollmentOpen;
    private Integer teacherId;
    private DayOfTheWeek dayOfTheWeek;

    @Override
    public String toString() {
        return this.name;
    }
}

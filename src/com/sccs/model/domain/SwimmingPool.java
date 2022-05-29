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
public class SwimmingPool implements Serializable {

    private Integer number;
    private String name;
    private Integer averageAge;
    private Integer maxCapacity;
    private Integer lanesNumber;
    private Double width;
    private Double length;
    private Double depth;

    @Override
    public String toString() {
        return this.name;
    }
}

package com.sccs.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnrollReport {
    
    private Integer quantity;
    private Integer vacancies;
    private Double average;
    private DayOfTheWeek day;
}

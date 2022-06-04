package com.sccs.model.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
@NoArgsConstructor
public class Student extends Person implements Serializable {
    
    private LocalDate birthDate;
    private Integer age;

    public Student(Integer id, String cpf, String name, LocalDate birthDate) {
        super(id, cpf, name);
        this.birthDate = birthDate;
        updateAge(this.birthDate);
    }
    
    public Student(String cpf, String name, LocalDate birthDate) {
        this.cpf = cpf;
        this.name = name;
        this.birthDate = birthDate;
        updateAge(this.birthDate);
    }
    
    public void updateAge(LocalDate date) {
        final LocalDate nowDate = LocalDate.now();
        final Period period = Period.between(birthDate, nowDate);
        this.age = period.getYears();
    }
}
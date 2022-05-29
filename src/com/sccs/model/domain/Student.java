package com.sccs.model.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Student extends Person implements Serializable {
    private LocalDate age;

    public Student(Integer id, String cpf, String name, LocalDate age) {
        super(id, cpf, name);
        this.age = age;
    }
}
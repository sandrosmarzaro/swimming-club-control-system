package com.sccs.model.domain;

import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class Teacher extends Person implements Serializable {

    public Teacher(Integer id, String cpf, String name) {
        super(id, cpf, name);
    }
    
    public Teacher(String cpf, String name) {
        this.cpf = cpf;
        this.name = name;
    }
}
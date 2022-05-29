package com.sccs.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Person {

    protected Integer id;
    protected String cpf;
    protected String name;

    @Override
    public String toString() {
        return this.name;
    }
}
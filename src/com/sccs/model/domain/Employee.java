package com.sccs.model.domain;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Employee extends Person implements Serializable {

    private final String login;
    private final String password;

    public Employee(Integer id, String cpf, String name, String login, String password) {
        super(id, cpf, name);
        this.login = login;
        this.password = password;
    }
}
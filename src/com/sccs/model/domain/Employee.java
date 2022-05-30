package com.sccs.model.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Getter
@NoArgsConstructor
public class Employee extends Person implements Serializable {

    private String login;
    private String password;

    public Employee(Integer id, String cpf, String name, String login, String password) {
        super(id, cpf, name);
        this.login = login;
        this.password = password;
    }
    
    public Employee(String cpf, String name, String login, String password) {
        this.cpf = cpf;
        this.name = name;
        this.login = login;
        this.password = password;
    }
}
package com.example.model;

import java.util.List;

public class Employee {
    private String name;
    private List<Employee> directStaffs;

    public Employee() {
    }

    public Employee(String name, List<Employee> directStaffs) {
        this.name = name;
        this.directStaffs = directStaffs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getDirectStaffs() {
        return directStaffs;
    }

    public void setDirectStaffs(List<Employee> directStaffs) {
        this.directStaffs = directStaffs;
    }
}
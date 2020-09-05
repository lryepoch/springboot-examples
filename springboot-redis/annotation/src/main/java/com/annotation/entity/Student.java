package com.annotation.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Student implements Serializable {

    private Integer id;

    private Integer age;

    private String sex;

    private String address;


}

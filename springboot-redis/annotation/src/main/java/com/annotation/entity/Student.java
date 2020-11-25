package com.annotation.entity;

import lombok.Data;

import java.io.Serializable;

/**
* @description 实体类需要实现序列化接口
* @author lryepoch
* @date 2020/11/25 16:58
*
*/
@Data
public class Student implements Serializable {

    private static final long serialVersionUID = -3766206919482082016L;

    private Integer id;

    private Integer age;

    private String sex;

    private String address;


}

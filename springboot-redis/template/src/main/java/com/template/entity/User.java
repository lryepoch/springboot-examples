package com.template.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lryepoch
 * @date 2020/9/5 14:20
 * @description TODO
 */
@Data
public class User implements Serializable {
    private Long id;
    private String guid;
    private String name;
    private String age;
    private Date createTime;

}

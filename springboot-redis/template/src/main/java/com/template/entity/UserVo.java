package com.template.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lryepoch
 * @date 2020/11/25 19:07
 * @description TODO
 */
@Data
public class UserVo implements Serializable {
    private static final long serialVersionUID = -8543238068497825587L;

    public  static final String Table = "t_user";

    private Integer id;
    private String address;
    private String name;
    private Integer age;
}

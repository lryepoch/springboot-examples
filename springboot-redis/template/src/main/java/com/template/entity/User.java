package com.template.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lryepoch
 * @date 2020/9/5 14:20
 * @description TODO 需要序列化
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -7127235588873344001L;

    private Long id;
    private String guid;
    private String name;
    private String age;
    private Date createTime;

}

package com.v1.direct.object;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lryepoch
 * @date 2020/5/25 15:53
 * @description TODO
 */
@Data
public class User implements Serializable {

    private String name;

    private String password;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}

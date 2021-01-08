package com.plus.dao;

import com.plus.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 *
 *  这个接口继承 BaseMapper<User>后即可获得常用的增删改查的方法， 如果有其他复杂的操作，就再定义自己的方法，并同时定义mapping文件即可。
 *
 * </p>
 *
 * @author lryepoch
 * @since 2020-12-24
 */
public interface UserMapper extends BaseMapper<User> {

}

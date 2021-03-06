package com.plus.test.service.impl;

import com.plus.test.entity.User;
import com.plus.test.mapper.UserMapper;
import com.plus.test.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lryepoch
 * @since 2020-12-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

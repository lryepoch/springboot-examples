package com.annotation.service.impl;

import com.annotation.entity.Student;
import com.annotation.mapper.StudentMapper;
import com.annotation.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @description 使用redis注解
* @author lryepoch
* @date 2020/9/5 15:17
*
*/
@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentMapper studentMapper;

    @Override
    @Cacheable(value = "select", key = "'@Cacheable：' + #id")  //用于查询
    public Student selectStudentById(Integer id) {
        return studentMapper.selectStudentById(id);
    }

    @Override
    @CachePut(value = "add", key = "'@CachePut:' + #student")    //用于新增+更新
    public int addStudent(Student student) {
        return studentMapper.addStudent(student);
    }

    @Override
    @CacheEvict(value = "delete", key = "'@CacheEvict:' + #id")  //用于删除
    public int deleteStudent(Integer id) {
        return studentMapper.deleteStudent(id);
    }


}

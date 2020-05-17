package com.redis.service.impl;

import com.redis.entity.Student;
import com.redis.mapper.StudentMapper;
import com.redis.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    @Cacheable(value = "stu-dent", key = "'哈哈哈哈哈：' + #id")
//    @CachePut(value = "user", key = "#id")
//    @CacheEvict(value = "user", key = "#id")
    public Student selectStudentById(Integer id) {
        return studentMapper.selectStudentById(id);
    }

    @Override
    public int addStudent(Student student) {
        return studentMapper.addStudent(student);
    }

}

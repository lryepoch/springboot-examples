package com.redis.mapper;

import com.redis.entity.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper {
    Student selectStudentById(Integer id);

    int addStudent(Student student);

}

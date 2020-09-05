package com.annotation.mapper;

import com.annotation.entity.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper {
    Student selectStudentById(Integer id);

    int addStudent(Student student);

    int deleteStudent(Integer id);
}

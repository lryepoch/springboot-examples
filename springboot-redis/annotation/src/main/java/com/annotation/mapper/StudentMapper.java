package com.annotation.mapper;

import com.annotation.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {
    Student selectStudentById(Integer id);

    int addStudent(Student student);

    int deleteStudent(Integer id);

    int updateStudent(Student student);

    List<Student> getAllStudents();
}

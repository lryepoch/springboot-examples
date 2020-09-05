package com.annotation.service;

import com.annotation.entity.Student;

public interface StudentService {
    Student selectStudentById(Integer id);

    int addStudent(Student student);

    int deleteStudent(Integer id);
}

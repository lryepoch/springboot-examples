package com.redis.service;

import com.redis.entity.Student;

public interface StudentService {
    Student selectStudentById(Integer id);

    int addStudent(Student student);
}

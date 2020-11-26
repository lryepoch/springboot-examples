package com.annotation.service;

import com.annotation.entity.Student;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;

import java.util.List;

public interface StudentService {
    Student selectStudentById(Integer id);

    int addStudent(Student student);

    int deleteStudent(Integer id);

    Student updateStudent(Student student);

    List<Student> getAllStudents();

    Student addStudent1(Student student);

    @Caching(
            put = {@CachePut(value = "userCache", key = "#user.id")},
            evict = {@CacheEvict(value = "allUsersCache", allEntries = true)}
    )
    Student updateStudent1(Student student);

    @Caching(
            evict = {
                    @CacheEvict(value = "userCache", key = "#id"),
                    @CacheEvict(value = "allUsersCache", allEntries = true)
            }
    )
    int deleteStudent1(Integer id);
}

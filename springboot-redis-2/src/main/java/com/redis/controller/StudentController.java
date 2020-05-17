package com.redis.controller;

import com.redis.entity.Student;
import com.redis.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

@EnableCaching
@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping("student/{id}")
    public Student selectStudentById(@PathVariable("id") Integer id){
        return studentService.selectStudentById(id);
    }

    @PostMapping("student/add")
    public int addStudent(@RequestBody Student student){
        return studentService.addStudent(student);
    }

}

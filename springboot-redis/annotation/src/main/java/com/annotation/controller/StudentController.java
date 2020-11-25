package com.annotation.controller;

import com.annotation.entity.Student;
import com.annotation.service.StudentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class StudentController {
    @Resource
    private StudentService studentService;

    @GetMapping("/select/{id}")
    public Student selectStudentById(@PathVariable("id") Integer id){
        return studentService.selectStudentById(id);
    }

    @PostMapping("/add")
    public int addStudent(@RequestBody Student student){
        return studentService.addStudent(student);
    }

    @GetMapping("/delete/{id}")
    public int deleteStudent(@PathVariable Integer id){
        return studentService.deleteStudent(id);
    }

}

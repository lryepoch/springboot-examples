package com.annotation.controller;

import com.annotation.entity.Student;
import com.annotation.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

@EnableCaching
@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping("/select/{id}")
    public Student selectStudentById(@PathVariable("id") Integer id){
        return studentService.selectStudentById(id);
    }

    @PostMapping("/add")
    public int addStudent(@RequestBody Student student){
        return studentService.addStudent(student);
    }

    @RequestMapping("/delete/{id}")
    public int deleteStudent(@PathVariable Integer id){
        return studentService.deleteStudent(id);
    }

}

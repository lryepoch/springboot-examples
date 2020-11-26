package com.annotation.controller;

import com.annotation.entity.Student;
import com.annotation.service.StudentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class StudentController {
    @Resource
    private StudentService studentService;

    @GetMapping("/select/{id}")
    public Student selectStudentById(@PathVariable("id") Integer id){
        return studentService.selectStudentById(id);
    }

    @GetMapping("/findAll")
    public List<Student> findAllStudents(){
       return studentService.getAllStudents();
    }

    @PostMapping("/add")
    public int addStudent(@RequestBody Student student){
        return studentService.addStudent(student);
    }

    @PostMapping("/update")
    public Student updateStudent(@RequestBody Student student){
        return studentService.updateStudent(student);
    }

    @GetMapping("/delete/{id}")
    public int deleteStudent(@PathVariable Integer id){
        return studentService.deleteStudent(id);
    }



    @PostMapping("/add1")
    public Student addStudent1(@RequestBody Student student){
        return studentService.addStudent1(student);
    }

    @PostMapping("/update1")
    public Student updateStudent1(@RequestBody Student student){
        return studentService.updateStudent1(student);
    }

    @GetMapping("/delete1/{id}")
    public int deleteStudent1(@PathVariable Integer id){
        return studentService.deleteStudent1(id);
    }

}

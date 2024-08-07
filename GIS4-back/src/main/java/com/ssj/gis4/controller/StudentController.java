package com.ssj.gis4.controller;


import com.ssj.gis4.domain.Student;
import com.ssj.gis4.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ssj")
public class StudentController {

    @Autowired
    StudentMapper studentMapper;

    @GetMapping("/test1")
    public String test1(){
        Student student = studentMapper.selectStudentById(1L);
        System.out.println(student.toString());
        return  "hello,idea!";
    }

}

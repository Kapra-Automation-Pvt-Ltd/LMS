package com.example.LMS.studentRegistration.controller;

import com.example.LMS.studentRegistration.entity.Student;
import com.example.LMS.studentRegistration.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class StudentRegistrationController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/studentRegistration")
    public Student saveStudentRegistration(@Valid @RequestBody Student student){

        return studentService.saveStudentRegistration(student);

    }

}

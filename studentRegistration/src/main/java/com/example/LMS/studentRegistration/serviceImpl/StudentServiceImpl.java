package com.example.LMS.studentRegistration.serviceImpl;

import com.example.LMS.studentRegistration.entity.Student;
import com.example.LMS.studentRegistration.repository.StudentRepository;
import com.example.LMS.studentRegistration.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student saveStudentRegistration(Student student) {
        return studentRepository.save(student);
    }
}

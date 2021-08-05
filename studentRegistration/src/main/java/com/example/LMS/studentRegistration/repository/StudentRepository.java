package com.example.LMS.studentRegistration.repository;

import com.example.LMS.studentRegistration.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {



}

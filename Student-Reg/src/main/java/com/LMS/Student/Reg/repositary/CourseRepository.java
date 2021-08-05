package com.LMS.Student.Reg.repositary;

import com.LMS.Student.Reg.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {
}

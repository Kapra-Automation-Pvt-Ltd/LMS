package com.LMS.Student.Reg.service;

import com.LMS.Student.Reg.entity.Course;
import com.LMS.Student.Reg.entity.CourseFile;

import java.util.List;

public interface CourseService {
   List<Course> getAllCourses();
   Course save(Course course);
   Course findById(Long courseId);
   List<CourseFile> findFilesByCourseId(Long courseId);
   Course update(Course course);

   void deleteFilesByCourseId(Long courseId);

   void deleteCourse(Long courseId);


}

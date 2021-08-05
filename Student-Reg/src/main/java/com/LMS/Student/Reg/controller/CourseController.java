package com.LMS.Student.Reg.controller;

import com.LMS.Student.Reg.entity.Course;
import com.LMS.Student.Reg.entity.CourseFile;
import com.LMS.Student.Reg.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping(value = "/")
    public List<Course> courses(Model model){
        List<Course> courses=courseService.getAllCourses();
        model.addAttribute("courses",courses);
        model.addAttribute("course",new Course());
        model.addAttribute("courseFiles",new ArrayList<CourseFile>());
        model.addAttribute("isAdd",true);

      /*  return "view/course";*/
        return courses;
    }

    @PostMapping(value = "/save")
    /*redirect attribute after saving again call to  @GetMapping(value = "/")*/
    public Course save(@ModelAttribute Course course, RedirectAttributes redirectAttributes, Model model){
        Course dbCourse =courseService.save(course);
        if (dbCourse!=null){
            redirectAttributes.addFlashAttribute("successmassage","course is saved succesfully");
           /* return "redirect:/";*/
            return course;
        }else {
            model.addAttribute("errormessage","Course is not saved, Please try again");
            model.addAttribute("course",course);
            /*return "view/course";*/
            return course;
        }

    }

    @GetMapping(value = "/editcourse/{courseId}")
    public Course editcourse(@PathVariable Long courseId, Model model){
        Course course =courseService.findById(courseId);
        List<CourseFile> courseFiles = courseService.findFilesByUserId(courseId);
        List<Course> courses=courseService.getAllCourses();
        model.addAttribute("courses",courses);
        model.addAttribute("course",course);
        model.addAttribute("courseFiles",courseFiles);
        model.addAttribute("isAdd",false);

        /*return "view/course";*/
        return course;
    }


    @PostMapping(value = "/update")

    public Course update(@ModelAttribute Course course, RedirectAttributes redirectAttributes,Model model){
        Course dbCourse =courseService.update(course);
        if (dbCourse!=null){
            redirectAttributes.addFlashAttribute("successmassage","course is updated succesfully");
            /*return "redirect:/";*/
            return dbCourse;
        }else {
            model.addAttribute("errormessage","Course is not updated, Please try again");
            model.addAttribute("course",course);
            /*return "view/course";*/
            return dbCourse;
        }

    }

    @GetMapping(value = "/deletecourse/{courseId}")
    public String deletecourse(@PathVariable Long courseId, RedirectAttributes redirectAttributes){
        courseService.deleteFilesByCourseId(courseId);

        courseService.deleteCourse(courseId);
        redirectAttributes.addFlashAttribute("successmassage","course is deleted succesfully");
        return "course is deleted succesfully";
    }



}

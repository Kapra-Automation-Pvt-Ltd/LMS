package com.LMS.Student.Reg.controller;

import com.LMS.Student.Reg.entity.Course;
import com.LMS.Student.Reg.entity.CourseFile;
import com.LMS.Student.Reg.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FileUploadController {

    @Autowired
    private CourseService courseService;

    @GetMapping(value = "/")
    public String courses(Model model){
        List<Course> courses=courseService.getAllCourses();
        model.addAttribute("courses",courses);
        model.addAttribute("course",new Course());
        model.addAttribute("courseFiles",new ArrayList<CourseFile>());
        model.addAttribute("isAdd",true);

        return "view/course";
    }

    @PostMapping(value = "/save")
    /*redirect attribute after saving again call to  @GetMapping(value = "/")*/
    public String save(@ModelAttribute Course course, RedirectAttributes redirectAttributes,Model model){
        Course dbCourse =courseService.save(course);
        if (dbCourse!=null){
            redirectAttributes.addFlashAttribute("successmassage","course is saved succesfully");
            return "redirect:/";
        }else {
            model.addAttribute("errormessage","Course is not saved, Please try again");
            model.addAttribute("course",course);
            return "view/course";
        }

    }


    @GetMapping(value = "/editcourse/{courseId}")
    public String editcourse(@PathVariable Long courseId, Model model){
        Course course =courseService.findById(courseId);
        List<CourseFile> courseFiles = courseService.findFilesByCourseId(courseId);
        List<Course> courses=courseService.getAllCourses();
        model.addAttribute("courses",courses);
        model.addAttribute("course",course);
        model.addAttribute("courseFiles",courseFiles);
        model.addAttribute("isAdd",false);

        return "view/course";
    }

    @PostMapping(value = "/update")

    public String update(@ModelAttribute Course course, RedirectAttributes redirectAttributes,Model model){
        Course dbCourse =courseService.update(course);
        if (dbCourse!=null){
            redirectAttributes.addFlashAttribute("successmassage","course is updated succesfully");
            return "redirect:/";
        }else {
            model.addAttribute("errormessage","Course is not updated, Please try again");
            model.addAttribute("course",course);
            return "view/course";
        }

    }

    @GetMapping(value = "/deletecourse/{courseId}")
    public String deletecourse(@PathVariable Long courseId, RedirectAttributes redirectAttributes){
        courseService.deleteFilesByCourseId(courseId);

       courseService.deleteCourse(courseId);
        redirectAttributes.addFlashAttribute("successmassage","course is deleted succesfully");
        return "redirect:/";
    }



    @GetMapping(value = "/viewcourse/{courseId}")
    public String viewcourse(@PathVariable Long courseId, Model model){
        Course course =courseService.findById(courseId);
        List<CourseFile> courseFiles = courseService.findFilesByCourseId(courseId);
        List<Course> courses=courseService.getAllCourses();
        model.addAttribute("courses",courses);
        model.addAttribute("course",course);
        model.addAttribute("courseFiles",courseFiles);
        model.addAttribute("isAdd",false);

        return "view/viewcourse";
    }





}

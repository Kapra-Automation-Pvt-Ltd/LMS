package com.LMS.Student.Reg.controller;

import com.LMS.Student.Reg.entity.Course;
import com.LMS.Student.Reg.entity.CourseFile;
import com.LMS.Student.Reg.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    CourseService courseService;
    @Autowired
    private ServletContext context;

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
        List<CourseFile> courseFiles = courseService.findFilesByCourseId(courseId);
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

    @GetMapping(value = "/viewcourse/{courseId}")
    public List<CourseFile> viewcourse(@PathVariable Long courseId, Model model){
        Course course =courseService.findById(courseId);
        List<CourseFile> courseFiles = courseService.findFilesByCourseId(courseId);
        List<Course> courses=courseService.getAllCourses();
        model.addAttribute("courses",courses);
        model.addAttribute("course",course);
        model.addAttribute("courseFiles",courseFiles);
        model.addAttribute("isAdd",false);

        return courseFiles ;



}


    @GetMapping(value = "/downloadfile/{fileName}/{modifiedFileName}")
    public void downloadfile(@PathVariable String fileName, @PathVariable String modifiedFilename, HttpServletResponse response){

        String fullPath = context.getRealPath("/files/" + File.separator + modifiedFilename);
        File file =new File(fullPath);
        final int BUFFER_SIZE =4096;
        if (file.exists()){
            try {
                FileInputStream inputStream = new FileInputStream(file);
                String mimeType = context.getMimeType(fullPath);
                response.setContentType(mimeType);
                response.setHeader("Content-disposition","attachment;filename =" + fileName);
                OutputStream outputStream = response.getOutputStream();
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead =-1;
                while ((bytesRead = inputStream.read(buffer))!= -1){
                    outputStream.write(buffer, 0, bytesRead);
                }
                inputStream.close();
                outputStream.close();


            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @GetMapping(value = "/downloadfilesaszip/{courseId}")
    public void downloadfilesaszip(@PathVariable Long courseId, HttpServletResponse response){
        List<CourseFile> courseFiles = courseService.findFilesByCourseId(courseId);
        if(courseFiles!=null && courseFiles.size()>0){
            downloadzipfiles(courseFiles,"files.zip",response);
        }
    }

    private void downloadzipfiles(List<CourseFile> courseFiles, String zipName, HttpServletResponse response) {

        try {
            ByteArrayOutputStream baos =new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(baos);
            byte bytes[] =new byte[122048];
            for (CourseFile file : courseFiles){
                if (file!=null && StringUtils.hasText(file.getModifiedFileName())){
                    FileInputStream fis = new FileInputStream(context.getRealPath("/files/"+File.separator+file.getModifiedFileName()));
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    zos.putNextEntry(new ZipEntry(file.getFileName()));
                    int bytesRead;
                    while ((bytesRead = bis.read(bytes))!= -1){
                        zos.write(bytes, 0, bytesRead);
                    }
                    zos.closeEntry();
                    bis.close();
                    fis.close();
                }
            }

            zos.finish();
            baos.flush();
            zos.close();
            baos.close();

            byte[] zip =baos.toByteArray();
            ServletOutputStream sos =response.getOutputStream();
            response.setContentType("application/zip");
            response.setHeader("Content-disposition","attachment;filename =" + zipName);
            sos.write(zip);
            sos.flush();
            sos.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }




}

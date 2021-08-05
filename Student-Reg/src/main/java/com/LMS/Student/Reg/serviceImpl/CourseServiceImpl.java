package com.LMS.Student.Reg.serviceImpl;

import com.LMS.Student.Reg.entity.Course;
import com.LMS.Student.Reg.entity.CourseFile;
import com.LMS.Student.Reg.repositary.CourseFileRepository;
import com.LMS.Student.Reg.repositary.CourseRepository;
import com.LMS.Student.Reg.service.CourseService;
import com.LMS.Student.Reg.service.UploadPathService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.xml.crypto.Data;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UploadPathService uploadPathService;
    @Autowired
    private CourseFileRepository courseFileRepository;
    @Autowired
    private ServletContext context;

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course save(Course course) {
        course.setCreatedDate(new Date());
        Course dbCourse =courseRepository.save( course);
        if (dbCourse!=null && course.getFiles()!=null&& course.getFiles().size()>0){
            for (MultipartFile file :course.getFiles()){

                if(file!=null && StringUtils.hasText(file.getOriginalFilename())){

                    String fileName = file.getOriginalFilename();
                    String modifiedFileName = FilenameUtils.getBaseName(fileName)+"_"+System.currentTimeMillis()+"."+FilenameUtils.getExtension(fileName);
                    File storeFile =uploadPathService.getFilePath(modifiedFileName,"files");
                    if (storeFile!=null){
                        try {
                            FileUtils.writeByteArrayToFile(storeFile,file.getBytes());
                        }catch (Exception e){
                            e.printStackTrace();

                        }
                    }

                    CourseFile files =new CourseFile();
                    files.setFileExtension(FilenameUtils.getExtension(fileName));
                    files.setFileName(fileName);
                    files.setModifiedFileName(modifiedFileName);
                    files.setCourse(dbCourse);
                    courseFileRepository.save(files);

                }





            }
        }
        return dbCourse;
    }

    @Override
    public Course findById(Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isPresent()){
            return course.get();
        }
        return null;
    }

    @Override
    public List<CourseFile> findFilesByUserId(Long courseId) {
        return courseFileRepository.findFilesByUserId(courseId);

    }

    @Override
    public Course update(Course course) {
        course.setUpdatedDate(new Date());
        Course dbCourse =courseRepository.save( course);


        if (course!=null &&  course.getRemoveImages()!=null && course.getRemoveImages().size()>0){

            courseFileRepository.deleteFilesByUserIdAndImageNames(course.getCourseId(),course.getRemoveImages());

            /*to remove that images in server*/

            for (String file:course.getRemoveImages()){
                File dbFile =new File(context.getRealPath("/images/"+File.separator+file));
                if (dbFile.exists()){
                    dbFile.delete();
                }
            }
        }






        if (dbCourse!=null && course.getFiles()!=null&& course.getFiles().size()>0){
            for (MultipartFile file :course.getFiles()){

                if(file!=null && StringUtils.hasText(file.getOriginalFilename())){

                    String fileName = file.getOriginalFilename();
                    String modifiedFileName = FilenameUtils.getBaseName(fileName)+"_"+System.currentTimeMillis()+"."+FilenameUtils.getExtension(fileName);
                    File storeFile =uploadPathService.getFilePath(modifiedFileName,"files");
                    if (storeFile!=null){
                        try {
                            FileUtils.writeByteArrayToFile(storeFile,file.getBytes());
                        }catch (Exception e){
                            e.printStackTrace();

                        }
                    }

                    CourseFile files =new CourseFile();
                    files.setFileExtension(FilenameUtils.getExtension(fileName));
                    files.setFileName(fileName);
                    files.setModifiedFileName(modifiedFileName);
                    files.setCourse(dbCourse);
                    courseFileRepository.save(files);



                }


            }
        }
        return dbCourse;
    }

    @Override
    public void deleteFilesByCourseId(Long courseId) {
        List<CourseFile> courseFiles = courseFileRepository.findFilesByUserId(courseId);
        if (courseFiles == null && courseFiles.size()>0) {
            for (CourseFile dbFile: courseFiles){

                File dbStoreFile = new File(context.getRealPath("/images/"+File.separator+ dbFile.getModifiedFileName()));
                if (dbStoreFile.exists()){
                    dbStoreFile.delete();
                }
            }
            courseFileRepository.deleteFilesByCourseId(courseId);
        }
    }

    @Override
    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }
}

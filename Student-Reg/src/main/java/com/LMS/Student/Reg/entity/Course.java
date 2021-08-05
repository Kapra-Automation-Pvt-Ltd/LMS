package com.LMS.Student.Reg.entity;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "course")
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name = "course_id")
    private Long courseId;
    @Column(name = "course_name")
    private String courseName;
    @Column(name = "course_code")
    private String courseCode;
    @Column(name = "course_description")
    private String courseDescription;
    @Column(name = "course_course_duration")
    private String courseDuration;
    @Column(name = "course_created_date")
    private Date createdDate;
    @Column(name = "course_updated_date")
    private Date updatedDate;


    @Transient
    private List<MultipartFile> files =new ArrayList<MultipartFile>();
    @Transient
    private List<String> removeImages =new ArrayList<String>();

}

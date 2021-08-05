package com.example.LMS.studentRegistration.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;
@Data
@AllArgsConstructor/*constructor create with all arguments*/
@NoArgsConstructor /*create default constructor*/
@Entity
@Builder /*? builder patterns*/
public class Student {

    /*SECTION A: SECTION A: APPLIED PROGRAMME*/

    private String nameOfTheProgramme;
    private Date commencementDate;

    /*SECTION B: APPLICANT’S PARTICULARS*/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "student_id")
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(name = "student_name")
    private String studentName;

    @NotBlank(message = "NIC is required")
    @Length(max = 10,min = 0)
    @Column(name = "student_nic_no")
    private String nicNo;

    @Column(name = "student_gender")
    private String gender;

    @Column(name = "student_nationality")
    private String nationality;

    @Past /*should be a past date , @Future, @PastOrPresent*/
    @Column(name = "student_date_of_birth")
    private Date dateOfBirth;

    @Column(name = "student_status")
    private String status;

    @Column(name = "student_address_line1")
    private String addressLine1;

    @Column(name = "student_address_line2")
    private String addressLine2;

    @Column(name = "student_city")
    private String city;

    @Column(name = "student_postal_code")
    private String postalCode;

    @NotBlank(message = "Email is required")
    @Email(message = "enter valid email")
    @Column(name = "student_email")
    private String studentEmail;

    @Column(name = "student_mobile")
    private String mobileNo;

    /*section c*/

    @Column(name = "name_of_the_institution")
    private String instituteName;

    @Column(name = "level_code")
    private  String levelCode;

    @Column(name = "year_of_graduation")
    private String yearOfGraduation;

    @Column(name = "grade_or_gpa")
    private Long gpa;

    /*SECTION D*/

    @Column(name = "position")
    private String position;

    @Column(name = "name_of_employee")
    private String nameOfEmployer;

    @Column(name = "employee_address")
    private String employerAddress;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    /*SECTION E*/
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "tel_no")
    private String telNo;
    @Column(name = "relationship")
    private String relationship;


}

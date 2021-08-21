package com.payment.StudentPayment.Service;
import java.util.Optional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.StudentPayment.Entity.StudentPayment;
import com.payment.StudentPayment.Repository.StudentPaymentRepository;



@Service
public class StudentPaymentService {
	
	@Autowired
	private StudentPaymentRepository studentPaymentRepository;
	
	public void saveImage(StudentPayment studentPayment) {
		studentPaymentRepository.save(studentPayment);	
	}

	public List<StudentPayment> getAllActiveImages() {
		return studentPaymentRepository.findAll();
	}

	public Optional<StudentPayment> getImageById(Long id) {
		return studentPaymentRepository.findById(id);
	}
	
	public StudentPayment save(StudentPayment payment) {
        return studentPaymentRepository.save(payment);
    }
	
	 public StudentPayment get(Long id) {
	        return studentPaymentRepository.findById(id).get();
	    }

	




	
    
	     
	

}

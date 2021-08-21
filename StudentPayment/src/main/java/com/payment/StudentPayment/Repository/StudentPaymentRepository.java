package com.payment.StudentPayment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payment.StudentPayment.Entity.StudentPayment;

@Repository
public interface StudentPaymentRepository extends JpaRepository<StudentPayment, Long> {

}

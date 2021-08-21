package com.payment.StudentPayment.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payment.StudentPayment.Entity.Cash;

@RestController
@RequestMapping("/api/sample")
public class Sample {
	
	
	@RequestMapping (value= {"/{id}","/all/{id}"}, method = RequestMethod.POST)
	public Cash returnMain(@PathVariable ("id") Long id, @RequestParam ("name") String name){
		return new Cash(id,name);
		
		
	}
	
	

}

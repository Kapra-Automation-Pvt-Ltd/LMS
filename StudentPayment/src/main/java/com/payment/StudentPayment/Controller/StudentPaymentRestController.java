package com.payment.StudentPayment.Controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.payment.StudentPayment.Entity.StudentPayment;
import com.payment.StudentPayment.Repository.StudentPaymentRepository;
import com.payment.StudentPayment.Service.StudentPaymentService;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
@RestController
@RequestMapping("/api")
public class StudentPaymentRestController {

	@Value("${uploadDir}")
	private String uploadFolder;

	@Autowired
	private StudentPaymentService studentPaymentService;
	@Autowired
	private StudentPaymentRepository studentPaymentRepository;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@GetMapping(value = {"/", "/home"})
	  public String addPaymentPage() { return "index"; }

	public class WelcomeController {
		@RequestMapping(value = {"","/"}, method = RequestMethod.GET)
		public String testAccess(){
			return "Authorized User";
		}

	}

	@PostMapping("/image/saveImageDetails")
	public @ResponseBody ResponseEntity<?> createPayment(@RequestParam("bank") String bank,@RequestParam("paymentType") String paymentType,@RequestParam("status") String status,
			@RequestParam("amount") double amount, @RequestParam("branch") String branch, Model model, HttpServletRequest request
			,final @RequestParam("image") MultipartFile file) {
		try {
			//String uploadDirectory = System.getProperty("user.dir") + uploadFolder;
			String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
			log.info("uploadDirectory:: " + uploadDirectory);
			String fileName = file.getOriginalFilename();
			String filePath = Paths.get(uploadDirectory, fileName).toString();
			log.info("FileName: " + file.getOriginalFilename());
			if (fileName == null || fileName.contains("..")) {
				model.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
				return new ResponseEntity<>("Sorry! Filename contains invalid path sequence " + fileName, HttpStatus.BAD_REQUEST);
			}
			String[] banks = bank.split(",");
			String[] branches = branch.split(",");

			

			Date createDate = new Date();
			log.info("Bank: " + banks[0]+" "+filePath);
			log.info("branch: " + branches[0]);
			log.info("paymentType: " + paymentType);
			log.info("status: " + status);
			log.info("amount: " + amount);
			try {
				File dir = new File(uploadDirectory);
				if (!dir.exists()) {
					log.info("Folder Created");
					dir.mkdirs();
				}
				// Save the file locally
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
				stream.write(file.getBytes());
				stream.close();
			} catch (Exception e) {
				log.info("in catch");
				e.printStackTrace();
			}
			byte[] imageData = file.getBytes();
			StudentPayment imageGallery = new StudentPayment();
			imageGallery.setBank(banks[0]);
			imageGallery.setImage(imageData);
			imageGallery.setPaymentType(paymentType);
			imageGallery.setAmount(amount);
			imageGallery.setStatus(status);
			imageGallery.setBranch(branches[0]);

			imageGallery.setCreateDate(createDate);
			studentPaymentService.saveImage(imageGallery);
			log.info("HttpStatus===" + new ResponseEntity<>(HttpStatus.OK));
			return new ResponseEntity<>("Product Saved With File - " + fileName, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Exception: " + e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/image/display/{id}")
	@ResponseBody
	void showImage(@PathVariable("id") Long id, HttpServletResponse response, Optional<StudentPayment> studentPayment)
			throws ServletException, IOException {
		log.info("Id :: " + id);
		studentPayment = studentPaymentService.getImageById(id);
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(studentPayment.get().getImage());
		response.getOutputStream().close();
	}

	@GetMapping("/image/imageDetails")
	StudentPayment showPaymentDetails(@RequestParam("id") Long id) {
		Optional <StudentPayment> studentPayment = studentPaymentService.getImageById(id);
		return studentPayment.orElseGet(null);
	}
	
    @GetMapping("/allpayaments")
    public List<StudentPayment> courses(Model model){
        List<StudentPayment> payments=studentPaymentService.getAllActiveImages();
        model.addAttribute("courses",payments);
        model.addAttribute("course",new StudentPayment());
        model.addAttribute("courseFiles",new ArrayList<StudentPayment>());
        model.addAttribute("isAdd",true);
        return payments;
    }	


	@PutMapping("/payments/{id}")
	public ResponseEntity<Object> updateStudent(@RequestBody StudentPayment student, @PathVariable long id) {

		Optional<StudentPayment> studentOptional = studentPaymentRepository.findById(id);

		if (!studentOptional.isPresent())
			return ResponseEntity.notFound().build();

		student.setId(id);
		studentPaymentRepository.save(student);

		return ResponseEntity.noContent().build();
	}

	

}	




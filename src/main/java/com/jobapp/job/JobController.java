package com.jobapp.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {

	@Autowired
	JobService jobService;
	
	@GetMapping("/jobs")
	public ResponseEntity<List<Job>> findAll(){
		return ResponseEntity.ok(jobService.findAll());
	}
	
	@PostMapping("/jobs")
	public ResponseEntity<String> createJob(@RequestBody Job job)  {
		jobService.createJob(job);
		return new ResponseEntity<String>("Job added successfully",HttpStatus.CREATED);
	}
	
	@GetMapping("/jobs/{id}")
	public ResponseEntity<Job> getJobById(@PathVariable Long id) {
		Job job = jobService.getJobById(id);
		if (job !=null) {
			return new ResponseEntity<Job>(job, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/jobs/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id){
		boolean deleted = jobService.deleteJobById(id);
		if (deleted) {
			return new ResponseEntity<String>("Job Deleted Successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}

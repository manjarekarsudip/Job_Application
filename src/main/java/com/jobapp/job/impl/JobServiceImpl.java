package com.jobapp.job.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jobapp.job.Job;
import com.jobapp.job.JobService;

@Service
public class JobServiceImpl implements JobService {

	private List<Job> jobs = new ArrayList<>();
	private Long nextId = 1L;
	
	@Override
	public List<Job> findAll() {
		return jobs;
	}

	@Override
	public void createJob(Job job) {
		job.setId(nextId++);
		jobs.add(job);
	}

	@Override
	public Job getJobById(Long id) {
		
		for (Job job : jobs) {
			if(job.getId().equals(id)) {
				return job;
			}
		}
		return null;
	}

	@Override
	public boolean deleteJobById(Long id) {
		
		Iterator<Job> iterator = jobs.iterator();
		while (iterator.hasNext()) {
			Job job = iterator.next();
			if(job.getId().equals(id)) {
				iterator.remove();
				return true;
			}
		}
		
		return false;
	}

}

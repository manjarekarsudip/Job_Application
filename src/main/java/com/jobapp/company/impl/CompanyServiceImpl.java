package com.jobapp.company.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobapp.company.Company;
import com.jobapp.company.CompanyRepository;
import com.jobapp.company.CompanyService;
import com.jobapp.job.Job;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	
	@Override
	public List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

	@Override
	public void createCompany(Company company) {
		companyRepository.save(company);
	}

	@Override
	public Company getCompanyById(Long id) {
		return companyRepository.findById(id).orElse(null);
	}

	@Override
	public boolean deleteCompanyById(Long id) {
		try {
			companyRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean updateCompany(Long id, Company company) {
		Optional<Company> companyOptional = companyRepository.findById(id);
		
		if(companyOptional.isPresent()) {
			Company companyToUpdate = companyOptional.get();
			companyToUpdate.setName(company.getName());
			companyToUpdate.setDescription(company.getDescription());
			companyToUpdate.setJobs(company.getJobs());
			companyRepository.save(companyToUpdate);
			return true;
		}
	return false;
	}

}

package com.prathyusha.companyms.company.impl;


import com.prathyusha.companyms.company.Company;
import com.prathyusha.companyms.company.CompanyRepository;
import com.prathyusha.companyms.company.CompanyService;
import com.prathyusha.companyms.company.clients.ReviewClient;
import com.prathyusha.companyms.company.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

	private CompanyRepository companyRepo ;
	private ReviewClient reviewClient;
	
	public CompanyServiceImpl(CompanyRepository companyRepo,ReviewClient reviewClient) {
		
		this.companyRepo = companyRepo;
		this.reviewClient=reviewClient;
	}


	@Override
	public List<Company> getAllCompanies() {
		// TODO Auto-generated method stub
		return companyRepo.findAll();
	}


	@Override
	public boolean updateCompany(Company company, Long id) {
		// TODO Auto-generated method stub
		Optional<Company> companyOptional = companyRepo.findById(id);
		if( companyOptional.isPresent()) {
			Company companyToUpdate = companyOptional.get();
			companyToUpdate.setDescription(company.getDescription());
			companyToUpdate.setName(company.getName());
			//companyToUpdate.setJobs(company.getJobs());
			companyRepo.save(companyToUpdate);
        return true;
		}
		return false;
	}


	@Override
	public void createCompany(Company company) {
		// TODO Auto-generated method stub
		
		companyRepo.save(company);
		
	}


	@Override
	public boolean deleteCompany(Long id) {
		// TODO Auto-generated method stub
		if(companyRepo.existsById(id)) {
			companyRepo.deleteById(id);
			return true;
		}
		
		return false;
	}


	@Override
	public Company getCompanyById(Long id) {
		// TODO Auto-generated method stub
		return companyRepo.findById(id).orElse(null);

	}

	@Override
	public void updateCompanyRating(ReviewMessage reviewMessage) {

		System.out.println(reviewMessage.getDescription());

		Company company = companyRepo.findById(reviewMessage.getCompanyId())
				.orElseThrow(()->new NotFoundException("Company not found"+reviewMessage));

		double averageRating = reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());
		company.setRating(averageRating);
		companyRepo.save(company);


	}
}


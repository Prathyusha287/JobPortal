package com.prathyusha.jobappms.job.impl;



import com.prathyusha.jobappms.job.Job;
import com.prathyusha.jobappms.job.JobMapper;
import com.prathyusha.jobappms.job.JobRepository;
import com.prathyusha.jobappms.job.JobService;
import com.prathyusha.jobappms.job.clients.CompanyClient;
import com.prathyusha.jobappms.job.clients.ReviewClient;
import com.prathyusha.jobappms.job.dto.JobDTO;
import com.prathyusha.jobappms.job.external.Company;
import com.prathyusha.jobappms.job.external.Review;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
	//private List<Job> jobs = new ArrayList<>();
	
	JobRepository jobRepo;

	private CompanyClient companyClient;
	private ReviewClient reviewClient;

	int attempt=0;
	@Autowired
	RestTemplate restTemplate;
	
	
	
	public JobServiceImpl(JobRepository jobRepo, CompanyClient companyClient,ReviewClient reviewClient
	) {
		
		this.jobRepo = jobRepo;
		this.companyClient=companyClient;
		this.reviewClient = reviewClient;
	}

	@Override
	//@CircuitBreaker(name = "companyBreaker",fallbackMethod = "companyBreakerFallback")
	@Retry(name="companyBreaker",fallbackMethod = "companyBreakerFallback")
    @RateLimiter(name="companyBreaker")
	public List<JobDTO> findAll() {
       System.out.println("Attempt: "+ ++attempt);
		List<Job> jobs = jobRepo.findAll();
		List<JobDTO> jobDTOS = new ArrayList<>();



		return jobs.stream().map(this::convertToDto)
				.collect(Collectors.toList());
	}

	public List<String> companyBreakerFallback(Exception e){
		List<String> list = new ArrayList<>();
		list.add("Dummy");
		return list;
	}

	private JobDTO convertToDto(Job job){

		/*

		RestTemplate restTemplate = new RestTemplate();
		Company company = restTemplate.getForObject("http://localhost:8081/companies/"+ job.getCompanyId(), Company.class);
  		Company company = restTemplate.getForObject("http://companyms:8081/companies/"+ job.getCompanyId(), Company.class);

		ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
				"http://reviewms:8083/reviews?companyId=" + job.getCompanyId(),
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Review>>() {
				}
		);
		List<Review> reviews = reviewResponse.getBody();

		 */

		Company company= companyClient.getCompany(job.getCompanyId());

		List<Review> reviews =reviewClient.getReviews(job.getCompanyId());



		JobDTO jobDTO = JobMapper.mapToJobWithCompanyDto(job,company,reviews);


		return jobDTO;
	}

	@Override
	public void createJob(Job job) {
		// TODO Auto-generated method stub
		
		
		jobRepo.save(job) ;
		
	}
	
	@Override
	public JobDTO getJobById(Long id) {
		
		/*for (Job job : jobs) {
			
			if(job.getId().equals(id)) {
				return job;
			}
		}
		return null;*/

		Job job =jobRepo.findById(id).orElse(null);
		
	return  convertToDto(job);
		
	}

	@Override
	public boolean deleteJobById(Long id) {
		// TODO Auto-generated method stub
		/*Iterator<Job> iterator = jobs.iterator();
		while(iterator.hasNext()) {
			Job job = iterator.next();
			if(job.getId().equals(id)) {
				iterator.remove();
				return true;
			}
		}*/
		
		
	/*for (Job job : jobs) {
			
			if(job.getId().equals(id)) {
				System.out.println(job);
				jobs.remove(job);
				return true;
				
			}
		}
		return false;*/
		try {

			jobRepo.deleteById(id);
			return true;
		} catch(Exception e) {
			return false;
		}
		
	}

	@Override
	public boolean updateJob(Long id, Job updatedJob) {
		// TODO Auto-generated method stub
		/*for(Job job : jobs) {
			if(job.getId().equals(id)) {
				job.setTitle(updatedJob.getTitle());
				job.setDescription(updatedJob.getDescription());
				job.setMinSalary(updatedJob.getMinSalary());
				job.setMaxSalary(updatedJob.getMaxSalary());
				job.setLocation(updatedJob.getLocation());
				return true;
			}
		}
		return false;*/
		
		Optional<Job> joboptional = jobRepo.findById(id);
		if(joboptional.isPresent()) {
			Job job = joboptional.get();
			job.setTitle(updatedJob.getTitle());
			job.setDescription(updatedJob.getDescription());
			job.setMinSalary(updatedJob.getMinSalary());
			job.setMaxSalary(updatedJob.getMaxSalary());
			job.setLocation(updatedJob.getLocation());
			jobRepo.save(job);
        return true;
			
		}
		return false;
		
	}

}

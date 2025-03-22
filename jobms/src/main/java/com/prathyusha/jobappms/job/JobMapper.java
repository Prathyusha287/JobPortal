package com.prathyusha.jobappms.job;

import com.prathyusha.jobappms.job.dto.JobDTO;
import com.prathyusha.jobappms.job.external.Company;
import com.prathyusha.jobappms.job.external.Review;

import java.util.List;

public class JobMapper {

    public static JobDTO mapToJobWithCompanyDto(
            Job job,
            Company company, List<Review> reviews
    ){

        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setCompany(company);
        jobDTO.setDescription(job.getDescription());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setReview(reviews);

        return jobDTO;

    }
}

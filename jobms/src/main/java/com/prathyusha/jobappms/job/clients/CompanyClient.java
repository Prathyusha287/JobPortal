package com.prathyusha.jobappms.job.clients;

import com.prathyusha.jobappms.job.external.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name ="companyms" ,url = "${company-service.url}")
public interface CompanyClient {

    @GetMapping("/companies/{id}")
   Company getCompany(@PathVariable("id") Long companyid);
}

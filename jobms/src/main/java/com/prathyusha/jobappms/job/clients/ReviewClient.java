package com.prathyusha.jobappms.job.clients;


import com.prathyusha.jobappms.job.external.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name ="reviewms", url = "${company-service.url}")
public interface ReviewClient {


        @GetMapping("/reviews")
        List<Review> getReviews(@RequestParam("companyId") Long companyid);
    }


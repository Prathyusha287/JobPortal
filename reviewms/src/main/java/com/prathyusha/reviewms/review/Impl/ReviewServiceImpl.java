package com.prathyusha.reviewms.review.Impl;


import com.prathyusha.reviewms.review.Review;
import com.prathyusha.reviewms.review.ReviewRepository;
import com.prathyusha.reviewms.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	private ReviewRepository reviewRepo ;

	public ReviewServiceImpl(ReviewRepository reviewRepo) {
		
		this.reviewRepo = reviewRepo;

	}


	@Override
	public List<Review> getAllReviews(Long companyId) {
		// TODO Auto-generated method stub
		List<Review> reviews = reviewRepo.findByCompanyId(companyId);
		return reviews;
	}


	@Override
	public boolean createReview(Long companyId, Review review) {

		if(companyId != null && review !=null) {

			review.setCompanyId(companyId);
			reviewRepo.save(review);
			return true;
		}
		return false;
		
	}


	@Override
	public Review getReview( Long reviewId) {
		// TODO Auto-generated method stub
		return  reviewRepo.findById(reviewId).orElse(null);
	}


	@Override
	public boolean updateReview( Long reviewId, Review updatedReview) {
		// TODO Auto-generated method stub
		Review review = reviewRepo.findById(reviewId).orElse(null);
		if(review != null ) {
			review.setTitle(updatedReview.getTitle());
			review.setRating(updatedReview.getRating());
			review.setDescription(updatedReview.getDescription());
			review.setCompanyId(updatedReview.getCompanyId());
			reviewRepo.save(review);
			return true;
		}else {
			return false;
		}
		
	}


	@Override
	public boolean deleteReview( Long reviewId) {
		// TODO Auto-generated method stub
		 Review review = reviewRepo.findById(reviewId).orElse(null);
		if(review!=null){
			reviewRepo.delete(review);
			return true;
		}else {
			return false;
		}
		
	}


	

}

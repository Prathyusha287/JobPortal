package com.prathyusha.reviewms.review;

import com.prathyusha.reviewms.review.messaging.ReviewMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
	
	
	private static final int ResponseEntity = 0;
	private ReviewService reviewService  ;

	private ReviewMessageProducer reviewMessageProducer;



	public ReviewController(ReviewService reviewService,ReviewMessageProducer reviewMessageProducer) {
	
		this.reviewService = reviewService;
		this.reviewMessageProducer=reviewMessageProducer;
	}
	
    @GetMapping
	public ResponseEntity<List<Review>> getAllreviews(@RequestParam  Long companyId){
		
    	return new ResponseEntity<>(reviewService.getAllReviews(companyId),HttpStatus.OK);
	}

    @PostMapping
    public ResponseEntity<String> createReview(@RequestParam Long companyId ,@RequestBody Review review){

		boolean isReviewSaved = reviewService.createReview(companyId,review);
    	if(isReviewSaved) {
			reviewMessageProducer.sendMessage(review);
    	return new ResponseEntity<String>("Review added successfully",HttpStatus.CREATED); 
    	
    	}
		else{
			return new ResponseEntity<>("Review  failed to add ",HttpStatus.NOT_FOUND);

		}

    }
    
    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview( @PathVariable Long reviewId){
    	
    	return new ResponseEntity<Review>(reviewService.getReview( reviewId),HttpStatus.OK);
    }
    
    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId,@RequestBody Review updatedReview){
    	
    	boolean isReviewUpdated = reviewService.updateReview( reviewId, updatedReview);
    	if (isReviewUpdated) {
    		return new ResponseEntity<String>("Review updated successfully",HttpStatus.OK);
    	}
    	return new ResponseEntity<String>("Review not updated",HttpStatus.NOT_FOUND);
    }
    
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId){
    
    	boolean isReviewDeleted= reviewService.deleteReview( reviewId);
    	if (isReviewDeleted) {
    		return new ResponseEntity<String>("Review deleted successfully",HttpStatus.OK);
    	}
    	return new ResponseEntity<String>("Review not deleted",HttpStatus.NOT_FOUND);
    }

	@GetMapping("/averageRating")
	public Double getAverageReview(@RequestParam Long companyId){
		List<Review> reviewList = reviewService.getAllReviews(companyId);

		return reviewList.stream().mapToDouble(Review::getRating).average().orElse(0.0);

	}

	}

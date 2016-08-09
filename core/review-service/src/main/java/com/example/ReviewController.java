package com.example;

import com.example.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.List;

@RestController
@EnableAutoConfiguration
@RefreshScope
@RequestMapping("/reviews")
public class ReviewController {
	
	@RequestMapping(method=RequestMethod.GET, path="")
	public ResponseEntity<List<Review>> getAllReviews(@RequestParam int productId) {
		List<Review> reviews = new ArrayList<Review>();
		reviews.add(new Review(productId, 1, "Steve", "Feedback", "Greatest Product ever!!"));

		return new ResponseEntity<List<Review>>(reviews, HttpStatus.OK);
	}
	
}

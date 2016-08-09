package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RestController
@Configuration
@EnableAutoConfiguration
@RefreshScope
@RequestMapping("/recommendations")
public class RecommendationController {
	  
	@RequestMapping(method=RequestMethod.GET, path="")
	public String getAllRecommendations() {
		return "{recommendation: Two Thumbs Up}";
	}
	
}
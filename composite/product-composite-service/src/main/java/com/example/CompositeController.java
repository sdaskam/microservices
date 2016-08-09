package com.example;

import com.example.domain.Product;
import com.example.domain.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RestController
@RequestMapping("/products")
public class CompositeController {
	@Autowired
	private CompositeService compositeService;

	@RequestMapping(method=RequestMethod.GET, path="/{productId}")
	public ResponseEntity<ProductInfo> getProduct(@PathVariable int productId) {
		return compositeService.getProduct(productId);
	}
	
}

package com.example;

import com.example.domain.Product;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Configuration
@EnableAutoConfiguration
@RefreshScope
@RequestMapping("/products")
public class ProductController {
	private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);
	private Map<Integer, Product> products = ImmutableMap.of(123, new Product(123, "MacBook Pro", 4), 456, new Product(456, "iPhone 6", 6));

	@RequestMapping(method=RequestMethod.GET, path="")
	public ResponseEntity<Collection<Product>> getAllProducts() {
		return new ResponseEntity<Collection<Product>>(products.values(), HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.GET, path="/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable int productId) {
		LOG.info("Request for Product ID: {}", productId);
		if (productId == 123) {
			return new ResponseEntity<Product>(new Product(productId, "MacBook Pro", 4), HttpStatus.OK);
		} else {
			return new ResponseEntity<Product>(new Product(productId, "iPhone 6", 6), HttpStatus.OK);
		}
	}
	
}

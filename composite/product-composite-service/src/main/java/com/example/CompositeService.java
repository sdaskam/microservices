package com.example;

import com.example.domain.Product;
import com.example.domain.ProductInfo;
import com.example.domain.Review;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CompositeService {
    private static final Logger LOG = LoggerFactory.getLogger(CompositeServiceApplication.class);

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "defaultProduct")
    public ResponseEntity<ProductInfo> getProduct(int productId) {
        LOG.debug("Calling getProduct with Hystrix protection");

        // Test for the fallback
        if (productId == 999) {
            throw new RuntimeException("Service Failure");
        }

        String url = "http://product-service/products/" + productId;
        ResponseEntity<Product> productResponseEntity = restTemplate.exchange(url, HttpMethod.GET, null, Product.class);

        ResponseEntity<List<Review>> reviewResponseEntity = this.getReviews(productId);

        ProductInfo productInfo = new ProductInfo();
        productInfo.setProduct(productResponseEntity.getBody());
        if (reviewResponseEntity.getStatusCode() == HttpStatus.OK) {
            productInfo.setReviews(reviewResponseEntity.getBody());
        }

        return new ResponseEntity<ProductInfo>(productInfo, HttpStatus.OK);
    }

    /**
     * Fallback method for getProduct()
     *
     * @param productId
     * @return
     */
    public ResponseEntity<ProductInfo> defaultProduct(int productId) {
        LOG.warn("Using fallback method for product-service");
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProduct(new Product(productId, "Fallback Product", -1));
        List<Review> reviews = new ArrayList<Review>();
        reviews.add(new Review(productId, 1, "Steve", "This Product Sucks", "Do not use it!!!"));
        productInfo.setReviews(reviews);

        return new ResponseEntity<ProductInfo>(productInfo, HttpStatus.OK);
    }

    @HystrixCommand(fallbackMethod = "defaultReviews")
    public ResponseEntity<List<Review>> getReviews(int productId) {
        LOG.debug("Calling getReviews with Hystrix protection");

        String url = "http://review-service/reviews?productId=" + productId;

        ResponseEntity<List<Review>> responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Review>>() {
                        });

        return responseEntity;
    }


    /**
     * Fallback method for getReviews()
     *
     * @param productId
     * @return
     */
    public ResponseEntity<List<Review>> defaultReviews(int productId) {
        LOG.warn("Using fallback method for review-service");
        List<Review> reviews = Arrays.asList(new Review(productId, 1, "Fallback Author 1", "Fallback Subject 1",
                "Fallback Content 1"));
        return new ResponseEntity<List<Review>>(reviews, HttpStatus.OK);
    }
}

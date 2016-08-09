package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableHystrixDashboard
public class MonitorDashboardApplication {

	@RequestMapping("/")
	public String home() {
		return "forward:/hystrix";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MonitorDashboardApplication.class, args);
	}
}

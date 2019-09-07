package com.hystrix.poc.delegate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import static org.springframework.http.HttpMethod.GET;

@Service
public class StudentServiceDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceDelegate.class);

    @Value("${school.service.uri}")
    private String schoolServiceUri;

    private RestTemplate restTemplate;

    @Autowired
    public StudentServiceDelegate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "callStudentServiceAndGetData_Fallback")
    public String callStudentServiceAndGetData(String schoolName) {
        LOGGER.info("Getting School details for {}", schoolName);

        String response = restTemplate
                .exchange(schoolServiceUri + "/getStudentDetailsForSchool/{schoolName}",
                        GET,
                        null,
                        new ParameterizedTypeReference<String>() {}, schoolName)
                .getBody();

        LOGGER.info("Response Received as {}", response + " -  " + new Date());

        return "NORMAL FLOW !!! - School Name -  " + schoolName + " :::  " +
                " Student Details " + response + " -  " + new Date();
    }

    @SuppressWarnings("unused")
    private String callStudentServiceAndGetData_Fallback(String schoolName) {

        LOGGER.info("Student Service is down!!! Fallback route enabled.");

        return "CIRCUIT BREAKER ENABLED!!! No Response From Student Service at this moment. " +
                " Service will be back shortly - " + new Date();
    }
}
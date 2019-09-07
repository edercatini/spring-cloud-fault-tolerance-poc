package com.hystrix.poc.controller;

import com.hystrix.poc.delegate.StudentServiceDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SchoolServiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchoolServiceController.class);

    private StudentServiceDelegate studentServiceDelegate;

    @Autowired
    public SchoolServiceController(StudentServiceDelegate studentServiceDelegate) {
        this.studentServiceDelegate = studentServiceDelegate;
    }

    @GetMapping("/getSchoolDetails/{schoolName}")
    public String getStudents(@PathVariable String schoolName) {
        LOGGER.info("Going to call student service to get data!");
        return studentServiceDelegate.callStudentServiceAndGetData(schoolName);
    }
}